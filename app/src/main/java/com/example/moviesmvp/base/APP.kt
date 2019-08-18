package com.example.moviesmvp.base

import android.app.Application
import com.example.moviesmvp.base.modules.moduleBase
import com.example.moviesmvp.base.modules.moduleListModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Suppress("unused")
class APP() : Application() {
    private val modules = listOf(moduleBase, moduleListModules)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@APP)
            module { modules }
        }
    }

}