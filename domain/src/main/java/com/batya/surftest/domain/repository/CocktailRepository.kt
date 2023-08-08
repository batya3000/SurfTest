package com.batya.surftest.domain.repository

import com.batya.surftest.domain.model.Cocktail
import com.batya.surftest.domain.model.Result

interface CocktailRepository {

    suspend fun getCocktails(): Result<List<Cocktail>>
    suspend fun getCocktailById(id: String): Result<Cocktail>
    suspend fun delete(id: String)

    suspend fun insert(cocktail: Cocktail)
    suspend fun update(cocktail: Cocktail)
}