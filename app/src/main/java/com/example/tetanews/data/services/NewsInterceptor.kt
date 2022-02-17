package com.example.tetanews.data.services

import com.example.tetanews.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NewsInterceptor: Interceptor {

    companion object{
        private const val HEADER_STR = "X-Api-Key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request()
            .newBuilder()
            .addHeader(HEADER_STR, BuildConfig.API_KEY)
            .build()

        return chain.proceed(newRequest)
    }

}