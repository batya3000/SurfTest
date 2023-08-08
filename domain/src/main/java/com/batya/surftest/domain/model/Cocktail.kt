package com.batya.surftest.domain.model

import java.util.UUID
import kotlin.String


data class Cocktail(
    var id: String = UUID.randomUUID().toString(),
    var name: String?,
    var image: String?,
    var ingredients: List<String> = listOf(),
    var description: String?,
    var recipe: String?
)

