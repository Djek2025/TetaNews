package com.example.tetanews.data

import com.example.tetanews.data.services.NewsService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class NewsRepository(private val service: NewsService) {

    suspend fun fetchNews() = flow {
        try {
            val response = service.getNewsResponse()
            if (response.isSuccessful){
                response.body()?.let {
                    emit(it)
                }
            }
        }catch (e: HttpException){

        }
    }

}