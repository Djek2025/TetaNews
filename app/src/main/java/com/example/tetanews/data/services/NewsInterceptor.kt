package com.example.tetanews.data.services

import com.example.tetanews.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NewsInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request()
            .newBuilder()
            .addHeader("X-Api-Key", BuildConfig.API_KEY)
            .build()

        return chain.proceed(newRequest)
    }

}