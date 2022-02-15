package com.example.tetanews.di

import android.app.Application
import com.example.tetanews.ui.NewsListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface AppComponent {

    fun inject(target: NewsListFragment)


    @Component.Builder
    interface Builder{

        @BindsInstance
        fun app(application: Application): Builder

        fun build(): AppComponent
    }

}