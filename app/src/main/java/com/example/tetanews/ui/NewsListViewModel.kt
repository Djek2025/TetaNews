package com.example.tetanews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tetanews.data.NewsRepository
import com.example.tetanews.data.models.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

class NewsListViewModel(private val repo: NewsRepository): ViewModel() {

    init {
        refresh()
    }

    private val _news = MutableSharedFlow<NewsResponse>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 0
    )
    val news = _news.asSharedFlow()

    fun refresh(){
        viewModelScope.launch(Dispatchers.IO) {
            _news.emitAll(repo.fetchNews())
        }
    }

}