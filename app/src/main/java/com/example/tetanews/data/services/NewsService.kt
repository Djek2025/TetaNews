package com.example.tetanews.data.services

import com.example.tetanews.data.models.NewsResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"

        fun create(): NewsService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient
                    .Builder()
                    .addInterceptor(NewsInterceptor())
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @GET("top-headlines")
    suspend fun getNewsResponse(
        @Query("country")country: String = "ua"
    ): Response<NewsResponse>

}