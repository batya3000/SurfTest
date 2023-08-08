package com.batya.surftest.app.presentation.edit

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batya.surftest.R
import com.batya.surftest.app.presentation.edit.viewmodel.EditCocktailViewModel
import com.batya.surftest.app.util.gone
import com.batya.surftest.app.util.visible
import com.batya.surftest.data.ARG_ID
import com.batya.surftest.databinding.DialogIngredientBinding
import com.batya.surftest.databinding.FragmentEditCocktailBinding
import com.batya.surftest.domain.model.Cocktail
import com.batya.surftest.domain.model.Result
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID


class EditCocktailFragment : Fragment() {

    private var _binding: FragmentEditCocktailBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModel<EditCocktailViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCocktailBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCocktail(arguments?.getString(ARG_ID))

        observeViewModel()
        addTitleListener()


        binding.bnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.bnDelete.setOnClickListener {
            if (arguments?.getString(ARG_ID) == null) findNavController().navigate(R.id.action_editCocktail_to_myCocktailsFragment)
            else {
                buildAlertDialog("Warning", "Are you sure you want to remove the cocktail?") {
                    deleteCocktail()
                    findNavController().navigate(R.id.action_editCocktail_to_myCocktailsFragment)
                }
            }

        }
        binding.bnSave.setOnClickListener {
            if (binding.etTitle.text != null && binding.etTitle.text!!.isNotEmpty() && binding.chipGroup.childCount > 0) {
                saveCocktail()
                findNavController().navigate(R.id.action_editCocktail_to_myCocktailsFragment)
            }
        }
        binding.cvAddIngredient.setOnClickListener {
            showAddIngredientDialog {
                if (it != null) {
                    addChip(it)
                }
            }
        }
    }

    private fun addChip(it: String) {
        val chip = Chip(context).apply {
            text = it
        }
        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip)
        }
        binding.chipGroup.addView(chip)
    }

    private fun buildAlertDialog(title: String, message: String, onPositiveClicked: () -> Unit) {
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialogSmallCorners)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Да") { _, _ ->
            onPositiveClicked()
            //TODO()
        }
        builder.setNegativeButton("Нет") { _, _ -> }
        builder.show()
    }

    private fun showAddIngredientDialog(ingredient: (String?) -> Unit) {

        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog).create()
        val dialogBinding: DialogIngredientBinding = DialogIngredientBinding.inflate(layoutInflater)

        with(builder) {
            setView(dialogBinding.root)
            setCancelable(false)
            dialogBinding.etIngredient.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s?.trim()?.isNotEmpty() == true) dialogBinding.layoutIngredient.error = null
                    else dialogBinding.layoutIngredient.error = "Add title"
                }
            })

            dialogBinding.bnAdd.setOnClickListener {
                if (dialogBinding.etIngredient.text?.trim()?.isNotEmpty() == true) {
                    ingredient(dialogBinding.etIngredient.text.toString())
                    builder.dismiss()
                } else {
                    //ingredient(null)
                }

            }
            dialogBinding.bnCancel.setOnClickListener {
                ingredient(null)
                builder.dismiss()
            }
            show()
        }
    }

    private fun observeViewModel() {
        viewModel.cocktail.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    successUI(it.successData)
                }
                is Result.Loading -> {
                    loadingUI()
                }
                is Result.Failure -> {
                    errorUI(it.errorMessage)
                    Log.d("TAG", "observeViewModel: error ${it.errorMessage}")
                }
            }
        }
    }

    private fun successUI(cocktail: Cocktail) {
        with(binding) {
            progressIndicator.gone()
            layoutError.gone()


            layoutContent.visible()
            etTitle.setText(cocktail.name)
            etDescription.setText(cocktail.description)
            etRecipe.setText(cocktail.recipe)
            //cvPhoto.

            initChips(cocktail.ingredients)
        }


    }

    private fun initChips(ingredients: List<String>) {
        Log.d("TAG", "initChips: ingredients=${ingredients.isNotEmpty()} (${ingredients.size})")
        if (binding.chipGroup.isEmpty()) {
            if (ingredients.isNotEmpty()) {
                ingredients.filter { it != ""}.forEach {
                    val chip = Chip(context).apply {
                        text = it.trim()
                    }
                    chip.setOnCloseIconClickListener {
                        binding.chipGroup.removeView(chip)
                    }
                    binding.chipGroup.addView(chip)
                }
            }
        }
    }

    private fun errorUI(errorMessage: String) {
        with(binding) {
            layoutContent.gone()
            progressIndicator.gone()

            layoutError.visible()
            tvError.text = "Ошибка! $errorMessage"
        }
    }

    private fun loadingUI() {
        with(binding) {
            layoutContent.gone()
            layoutError.gone()

            progressIndicator.visible()
        }
    }

    private fun deleteCocktail() {
        if (arguments?.getString(ARG_ID) != null) viewModel.deleteCocktail(arguments?.getString(ARG_ID)!!)

    }

    private fun saveCocktail() {
        val name = binding.etTitle.text.toString()
        val description =
            if (binding.etDescription.text == null) null
            else binding.etDescription.text.toString()
        val recipe =
            if (binding.etRecipe.text == null) null
            else binding.etRecipe.text.toString()
        //val ingredients = listOf("Аа", "Бб")
        val ingredients =
            if (binding.chipGroup.childCount != 0) binding.chipGroup.children.map {(it as Chip).text.toString()}.toList()
            else listOf()
        Log.d("TAG", "saveCocktail: ingredients=$ingredients")
        val cocktail = Cocktail(
            id = arguments?.getString(ARG_ID) ?: UUID.randomUUID().toString(),
            name = name,
            description = description,
            recipe = recipe,
            image = null,
            ingredients = ingredients
        )

        viewModel.saveCocktail(cocktail, arguments?.getString(ARG_ID) == null)
    }

    private fun addTitleListener() {
        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.trim()?.isNotEmpty() == true) {
                    binding.layoutTitle.error = null
                } else {
                    binding.layoutTitle.error = "Add title"
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}