package com.ameen.newsapp.util

sealed class ResponseWrapper<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : ResponseWrapper<T>(data)
    class Error<T>(data: T?, message: String) : ResponseWrapper<T>(data, message)
    class Loading<T> : ResponseWrapper<T>()

}
