package com.batya.surftest.app

import android.app.Application
import com.batya.surftest.app.di.appModule
import com.batya.surftest.app.di.dataModule
import com.batya.surftest.app.di.domainModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    dataModule,
                    domainModule
                )
            )
        }

    }
}