package com.batya.surftest.app.presentation.edit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.batya.surftest.domain.model.Cocktail
import com.batya.surftest.domain.model.Result
import com.batya.surftest.domain.usecase.DeleteCocktailByIdUseCase
import com.batya.surftest.domain.usecase.GetCocktailByIdUseCase
import com.batya.surftest.domain.usecase.InsertCocktailUseCase
import com.batya.surftest.domain.usecase.UpdateCocktailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EditCocktailViewModel(
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase,
    private val insertCocktailUseCase: InsertCocktailUseCase,
    private val updateCocktailUseCase: UpdateCocktailUseCase,
    private val deleteCocktailByIdUseCase: DeleteCocktailByIdUseCase

) : ViewModel() {
   var cocktail: LiveData<Result<Cocktail>> = liveData(context = viewModelScope.coroutineContext) {
        emit(Result.Loading)
    }



    fun getCocktail(id: String?) {
        cocktail = liveData(context = viewModelScope.coroutineContext) {
            if (id == null) emit(Result.Success(
                Cocktail(
                    name = null,
                    description = null,
                    recipe = null,
                    image = null,
                    ingredients = listOf()
                )
            ))
            else {
                emit(Result.Loading)
                //delay(1000)
                val result = getCocktailByIdUseCase(id)
                emit(result)
            }
        }
    }


    fun saveCocktail(cocktail: Cocktail, isNew: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
//            insertCocktailUseCase(cocktail)
            if (isNew) {
                insertCocktailUseCase(cocktail)
            } else {
                updateCocktailUseCase(cocktail)
            }
        }
    }
    fun deleteCocktail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCocktailByIdUseCase(id)
        }
    }
}