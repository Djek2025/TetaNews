package com.example.tetanews.data.models

import java.lang.Exception

data class NewsResponseWrapper(
    val status: Status,
    val response: NewsResponse?
)

sealed class Status{
    object Success: Status()
    class Error(val e: Exception): Status()
}
