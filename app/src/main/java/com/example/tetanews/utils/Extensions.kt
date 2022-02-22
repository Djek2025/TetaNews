package com.example.tetanews.utils

import android.annotation.SuppressLint
import com.example.tetanews.data.models.Article
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun Article.getTimeInHours(): String {
    val origISOformat = SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss'Z'")
    val targetFormat = SimpleDateFormat("HH:mm")
    return targetFormat.format(checkNotNull(origISOformat.parse(this.publishedAt)))
}