package com.example.tetanews.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tetanews.data.NewsRepository
import com.example.tetanews.data.services.NewsInterceptor
import com.example.tetanews.data.services.NewsApi
import com.example.tetanews.ui.NewsListAdapter
import com.example.tetanews.ui.NewsListViewModel
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
class MainModule() {

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi) = NewsRepository(api)

    @Provides
    @Singleton
    fun provideNewsAdapter() = NewsListAdapter()

    @Provides
    @Singleton
    fun provideNewsInterceptor() = NewsInterceptor()

    @Provides
    @Singleton
    fun provideOkhttpClient(interceptor: NewsInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(client: OkHttpClient): NewsApi{
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Suppress("UNCHECKED_CAST")
    @Provides
    @Singleton
    fun provideNewsViewModel(repo: NewsRepository, application: Application) = object : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewsListViewModel(repo, application) as T
        }
    }.create(NewsListViewModel::class.java)

}