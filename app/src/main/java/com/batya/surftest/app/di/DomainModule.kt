package com.batya.surftest.app.di

import com.batya.surftest.domain.usecase.DeleteCocktailByIdUseCase
import com.batya.surftest.domain.usecase.GetCocktailByIdUseCase
import com.batya.surftest.domain.usecase.GetCocktailsUseCase
import com.batya.surftest.domain.usecase.InsertCocktailUseCase
import com.batya.surftest.domain.usecase.UpdateCocktailUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<GetCocktailsUseCase> {
        GetCocktailsUseCase(repository = get())
    }
    factory<GetCocktailByIdUseCase> {
        GetCocktailByIdUseCase(repository = get())
    }
    factory<InsertCocktailUseCase> {
        InsertCocktailUseCase(repository = get())
    }
    factory<UpdateCocktailUseCase> {
        UpdateCocktailUseCase(repository = get())
    }
    factory<DeleteCocktailByIdUseCase> {
        DeleteCocktailByIdUseCase(repository = get())
    }
}