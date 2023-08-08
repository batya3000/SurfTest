package com.batya.surftest.domain.usecase

import com.batya.surftest.domain.model.Cocktail
import com.batya.surftest.domain.model.Result
import com.batya.surftest.domain.repository.CocktailRepository

class GetCocktailByIdUseCase(private val repository: CocktailRepository) {

    suspend operator fun invoke(id: String): Result<Cocktail> {
        return repository.getCocktailById(id)
    }
}