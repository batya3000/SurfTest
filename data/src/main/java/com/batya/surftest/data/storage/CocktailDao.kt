package com.batya.surftest.data.storage

import androidx.room.*
import com.batya.surftest.data.model.CocktailEntity

@Dao
interface CocktailDao {

    @Query("SELECT * FROM table_cocktail")
    suspend fun getCocktails(): List<CocktailEntity>

    @Query("SELECT * FROM table_cocktail WHERE id=:id")
    suspend fun getCocktailById(id: String): CocktailEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cocktail: CocktailEntity)

    @Update
    suspend fun update(cocktail: CocktailEntity)

    @Query("DELETE FROM table_cocktail WHERE id=:id")
    suspend fun delete(id: String)
}