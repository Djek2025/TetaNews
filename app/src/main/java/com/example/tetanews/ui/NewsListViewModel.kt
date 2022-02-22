package com.example.tetanews.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tetanews.data.NewsRepository
import com.example.tetanews.data.models.NewsResponse
import com.example.tetanews.data.models.NewsResponseWrapper
import com.example.tetanews.data.models.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

class NewsListViewModel(private val repo: NewsRepository, application: Application): AndroidViewModel(application) {

    init {
        refresh()
    }

    private val _news = MutableSharedFlow<NewsResponse?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 0
    )
    val news = _news.asSharedFlow()

    fun refresh(){
        viewModelScope.launch(Dispatchers.IO) {

            repo.fetchNews().collect {
                when(it.status){
                    is Status.Success -> _news.emit(it.response)
                    is Status.Error -> Toast.makeText(getApplication(), it.status.e.toString(), Toast.LENGTH_LONG).show()
                }
            }

        }
    }

}