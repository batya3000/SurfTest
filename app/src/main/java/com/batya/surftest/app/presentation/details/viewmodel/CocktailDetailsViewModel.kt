package com.batya.surftest.app.presentation.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.batya.surftest.domain.model.Cocktail
import com.batya.surftest.domain.model.Result
import com.batya.surftest.domain.usecase.GetCocktailByIdUseCase
import com.batya.surftest.domain.usecase.GetCocktailsUseCase
import kotlinx.coroutines.delay

class CocktailDetailsViewModel(
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase,
) : ViewModel() {

    var cocktail: LiveData<Result<Cocktail>> = liveData(context = viewModelScope.coroutineContext) {
        emit(Result.Loading)
    }

    fun getCocktail(id: String) {
        cocktail = liveData(context = viewModelScope.coroutineContext) {
            emit(Result.Loading)

            //delay(1000)
            val result = getCocktailByIdUseCase(id)
            emit(result)
        }
    }
}