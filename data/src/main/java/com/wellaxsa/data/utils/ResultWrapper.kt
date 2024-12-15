package com.wellaxsa.data.utils

sealed class ResultWrapper<out R> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error(val message: String) : ResultWrapper<Nothing>()
    object Loading : ResultWrapper<Nothing>()
}
