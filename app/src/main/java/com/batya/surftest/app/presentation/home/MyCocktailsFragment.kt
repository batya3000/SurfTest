package com.batya.surftest.app.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.batya.surftest.R
import com.batya.surftest.app.util.gone
import com.batya.surftest.app.util.visible
import com.batya.surftest.app.presentation.home.adapter.CocktailAdapter
import com.batya.surftest.app.presentation.home.viewmodel.MyCocktailsViewModel
import com.batya.surftest.data.ARG_ID
import com.batya.surftest.databinding.FragmentMyCocktailsBinding
import com.batya.surftest.domain.model.Cocktail
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.batya.surftest.domain.model.Result


class MyCocktailsFragment : Fragment() {

    private var _binding: FragmentMyCocktailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var cocktailAdapter: CocktailAdapter
    private val viewModel by viewModel<MyCocktailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyCocktailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        observeViewModel()
//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_MyCocktails_to_CocktailDetails)
//        }


    }

    private fun observeViewModel() {
        viewModel.items.removeObservers(viewLifecycleOwner)
        viewModel.items.observe(viewLifecycleOwner) {
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

    private fun successUI(cocktails: List<Cocktail>) {
        with(binding) {
            cocktailAdapter.items = cocktails
            if (cocktails.isEmpty()) {
                cvNoCocktails.visible()
                cvCocktails.gone()
            } else {
                cvNoCocktails.gone()
                cvCocktails.visible()
            }
            progressIndicator.gone()
            layoutError.gone()

            ivShare.setOnClickListener {
                createIntent(cocktails)
            }
        }
    }

    private fun createIntent(cocktails: List<Cocktail>) {
        var cocktailsText = cocktails.map { it.name }.take(4).joinToString(", ")
        if (cocktails.size > 4) cocktailsText += " и другие"

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Смотри какие коктейли я создал в приложении Cocktail Bar!\n" +
                    "$cocktailsText.\n" +
                    "Хочешь попробовать?"
        )
        shareIntent.type = "text/plain"
        startActivity(
            Intent.createChooser(
                shareIntent,
                "Share your recipes!"
            )
        )
    }

    private fun loadingUI() {
        with(binding) {
            cvNoCocktails.gone()
            cvCocktails.gone()
            binding.progressIndicator.visible()
            binding.layoutError.gone()
        }
    }
    private fun errorUI(errorMessage: String) {
        with(binding) {
            cvNoCocktails.gone()
            cvCocktails.gone()

            layoutError.visible()
            progressIndicator.gone()
            tvError.text = "Ошибка! $errorMessage"
        }
    }

    private fun setupAdapter() {

        cocktailAdapter = CocktailAdapter {
            findNavController().navigate(
                R.id.action_myCocktails_to_cocktailDetails,
                bundleOf(
                    ARG_ID to it
                )
            )
            //Toast.makeText(context, "Clicked $it", Toast.LENGTH_SHORT).show()
        }


        binding.recyclerView.apply {
            adapter = cocktailAdapter
            layoutManager = GridLayoutManager(context, 2)

        }

    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}