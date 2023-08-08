package com.batya.surftest.domain.usecase

import com.batya.surftest.domain.model.Cocktail
import com.batya.surftest.domain.repository.CocktailRepository

class InsertCocktailUseCase(private val repository: CocktailRepository) {

    suspend operator fun invoke(cocktail: Cocktail) {
        return repository.insert(cocktail)
    }
}