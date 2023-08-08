package com.batya.surftest.app.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.batya.surftest.domain.model.Cocktail
import com.batya.surftest.domain.model.Result
import com.batya.surftest.domain.usecase.GetCocktailsUseCase
import kotlinx.coroutines.delay

class MyCocktailsViewModel(
    private val getCocktailsUseCase: GetCocktailsUseCase,
) : ViewModel() {

    val items: LiveData<Result<List<Cocktail>>> = liveData(context = viewModelScope.coroutineContext) {
        emit(Result.Loading)
        //delay(300)
        val result = getCocktailsUseCase()
        emit(result)
    }

}