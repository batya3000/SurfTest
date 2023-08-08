package com.batya.surftest.app.di


import androidx.room.Room
import com.batya.surftest.data.repository.CocktailRepositoryImpl
import com.batya.surftest.data.storage.CocktailDatabase
import com.batya.surftest.domain.repository.CocktailRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module


val dataModule = module {

//    single { createOkHttpClient(context = get()) }
//    single { createWebService<PizzaApi>(okHttpClient = get(), url = Constants.BASE_URL) }

    single { Room.databaseBuilder(get(), CocktailDatabase::class.java, "cocktail_database").build() }

    single { get<CocktailDatabase>().cocktailDao()}

    single { CocktailRepositoryImpl(dao = get()) as CocktailRepository }

}