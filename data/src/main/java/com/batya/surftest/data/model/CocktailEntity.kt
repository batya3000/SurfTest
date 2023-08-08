package com.batya.surftest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "table_cocktail")
data class CocktailEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String?,
    val image: String?,
    val ingredients: List<String> = listOf(),
    val description: String? = null,
    val recipe: String? = null
)