package com.batya.surftest.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.batya.surftest.data.model.CocktailEntity
import com.batya.surftest.data.storage.CocktailDao


@Database(entities = [CocktailEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListTypeConverters::class)
abstract class CocktailDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao

}