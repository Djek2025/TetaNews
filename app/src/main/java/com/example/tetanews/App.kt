package com.example.tetanews

import android.app.Application
import com.example.tetanews.di.AppComponent
import com.example.tetanews.di.DaggerAppComponent

class App: Application() {

    private val dagger: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .app(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

    fun getDaggerComponent() = dagger

}