package com.batya.surftest.app.di


import com.batya.surftest.app.presentation.details.viewmodel.CocktailDetailsViewModel
import com.batya.surftest.app.presentation.edit.viewmodel.EditCocktailViewModel
import com.batya.surftest.app.presentation.home.viewmodel.MyCocktailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<MyCocktailsViewModel> {
        MyCocktailsViewModel(
            getCocktailsUseCase = get()
        )
    }
    viewModel<CocktailDetailsViewModel> {
        CocktailDetailsViewModel(
            getCocktailByIdUseCase = get()
        )
    }
    viewModel<EditCocktailViewModel> {
        EditCocktailViewModel(
            getCocktailByIdUseCase = get(),
            insertCocktailUseCase = get(),
            updateCocktailUseCase = get(),
            deleteCocktailByIdUseCase = get()
        )
    }

}