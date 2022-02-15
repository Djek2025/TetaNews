package com.example.tetanews.utils

import com.example.tetanews.data.models.Article
import java.text.SimpleDateFormat

fun Article.getTimeInHours(): String {
    val origISOformat = SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss'Z'")
    val targetFormat = SimpleDateFormat("HH:mm")
    return targetFormat.format(origISOformat.parse(this.publishedAt))
}