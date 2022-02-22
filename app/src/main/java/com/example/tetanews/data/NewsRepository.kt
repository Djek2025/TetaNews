package com.example.tetanews.data

import com.example.tetanews.data.models.NewsResponseWrapper
import com.example.tetanews.data.models.Status
import com.example.tetanews.data.services.NewsApi
import kotlinx.coroutines.flow.flow

class NewsRepository(private val api: NewsApi) {

    suspend fun fetchNews() = flow {
        try {
            val response = api.getNewsResponse()
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