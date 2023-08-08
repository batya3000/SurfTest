package com.batya.surftest.app.presentation.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.batya.surftest.R
import com.batya.surftest.app.presentation.details.viewmodel.CocktailDetailsViewModel
import com.batya.surftest.app.presentation.home.viewmodel.MyCocktailsViewModel
import com.batya.surftest.app.util.gone
import com.batya.surftest.app.util.visible
import com.batya.surftest.data.ARG_ID
import com.batya.surftest.databinding.FragmentCocktailDetailsBinding
import com.batya.surftest.domain.model.Cocktail
import com.batya.surftest.domain.model.Result
import org.koin.androidx.viewmodel.ext.android.viewModel


class CocktailDetailsFragment : Fragment() {

    private var _binding: FragmentCocktailDetailsBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModel<CocktailDetailsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailDetailsBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCocktail(arguments?.getString(ARG_ID)!!)

        observeViewModel()

        binding.bnEdit.setOnClickListener {
            findNavController().navigate(
                R.id.action_cocktailDetails_to_editCocktail,
                bundleOf(
                    ARG_ID to arguments?.getString(ARG_ID)!!
                ),
            )
        }
    }

    private fun observeViewModel() {
        viewModel.cocktail.removeObservers(viewLifecycleOwner)
        viewModel.cocktail.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    successUI(it.successData)
                }
                is Result.Loading -> {
                    loadingUI()
                }
                is Result.Failure -> {
                    //errorUI(it.errorMessage)
                    Log.d("TAG", "observeViewModel: error ${it.errorMessage}")
                }
            }
        }
    }

    private fun successUI(cocktail: Cocktail) {

        with(binding) {
            progressIndicator.gone()


            if (cocktail.name != null) {
                tvTitle.text = cocktail.name
            } else {
                tvTitle.gone()
            }
            if (cocktail.description != null && cocktail.description!!.isNotEmpty()) {
                tvDescription.visible()
                tvDescription.text = cocktail.description
            } else {
                tvDescription.gone()
            }
            if (cocktail.recipe != null && cocktail.recipe!!.isNotEmpty()) {
                tvRecipeTitle.visible()
                tvRecipe.visible()
                tvRecipe.text = cocktail.recipe
            } else {
                tvRecipeTitle.gone()
                tvRecipe.gone()
            }
            if (cocktail.ingredients.filter { it.trim() != "" }.isNotEmpty()) {
                tvIngredients.visible()
                val ingredients = cocktail.ingredients.filter { it.trim() != "" }.joinToString(separator = "\n-\n")
                tvIngredients.text = ingredients
            } else {
                tvIngredients.gone()
            }
        }
    }

    private fun loadingUI() {
        binding.progressIndicator.visible()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}