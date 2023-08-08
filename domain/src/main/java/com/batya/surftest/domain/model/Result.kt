package com.batya.surftest.domain.model

import kotlin.String

sealed class Result<out T> {
    class Success<out T>(val successData: T) : Result<T>()
    class Failure(val errorMessage: String) : Result<Nothing>()
    object Loading: Result<Nothing>()

}