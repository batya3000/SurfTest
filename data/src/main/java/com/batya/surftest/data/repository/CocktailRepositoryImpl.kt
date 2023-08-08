package com.batya.surftest.data.repository

import com.batya.surftest.data.storage.CocktailDao
import com.batya.surftest.data.wrapper.asDataModel
import com.batya.surftest.data.wrapper.asDomainModel
import com.batya.surftest.domain.model.Cocktail
import com.batya.surftest.domain.repository.CocktailRepository
import com.batya.surftest.domain.model.Result

class CocktailRepositoryImpl(private val dao: CocktailDao): CocktailRepository {

    override suspend fun getCocktails(): Result<List<Cocktail>> {
        return try {
            val result = dao.getCocktails()

            Result.Success(
                result.asDomainModel()
            )

        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun getCocktailById(id: String): Result<Cocktail> {
        return try {
            val result = dao.getCocktailById(id)

            Result.Success(
                result.asDomainModel()
            )

        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun insert(cocktail: Cocktail) {
        dao.insert(cocktail.asDataModel())
    }

    override suspend fun update(cocktail: Cocktail) {
        dao.update(cocktail.asDataModel())
    }

    override suspend fun delete(id: String) {
        dao.delete(id)
    }
}