package com.example.tetanews.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tetanews.data.NewsRepository
import com.example.tetanews.data.services.NewsService
import com.example.tetanews.ui.NewsListAdapter
import com.example.tetanews.ui.NewsListViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule() {

    @Provides
    @Singleton
    fun provideNewsService() = NewsService.create()

    @Provides
    @Singleton
    fun provideNewsRepository(service: NewsService) = NewsRepository(service)

    @Provides
    @Singleton
    fun provideNewsAdapter() = NewsListAdapter()

    @Provides
    @Singleton
    fun provideNewsViewModel(repo: NewsRepository) = object : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewsListViewModel(repo) as T
        }
    }.create(NewsListViewModel::class.java)

}