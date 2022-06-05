package com.lonard.camerlangproject.db

sealed class DataLoadResult<out R> private constructor() {
    data class Successful<out T>(val data: T): DataLoadResult<T>()
    data class Failed(val error: String): DataLoadResult<Nothing>()

    object Loading: DataLoadResult<Nothing>()
}
