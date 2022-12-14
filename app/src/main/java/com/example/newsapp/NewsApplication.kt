package com.example.newsapp

import android.app.Application
import com.example.newsapp.presentation.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(mainModule)
            androidContext(this@NewsApplication)
        }
    }
}