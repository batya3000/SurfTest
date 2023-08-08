package com.batya.surftest.domain.usecase

import com.batya.surftest.domain.repository.CocktailRepository

class DeleteCocktailByIdUseCase(private val repository: CocktailRepository) {

    suspend operator fun invoke(id: String) {
        return repository.delete(id)
    }
}