package com.example.tetanews.data

import com.example.tetanews.data.models.NewsResponseWrapper
import com.example.tetanews.data.models.Status
import com.example.tetanews.data.services.NewsService
import kotlinx.coroutines.flow.flow

class NewsRepository(private val service: NewsService) {

    suspend fun fetchNews() = flow {
        try {
            val response = service.getNewsResponse()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(
                        NewsResponseWrapper(Status.Success, it)
                    )
                }
            }
        } catch (e: Exception) {
            emit(
                NewsResponseWrapper(Status.Error(e), null)
            )
        }
    }

}