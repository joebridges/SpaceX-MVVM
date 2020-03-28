package com.spacex_mvvm.data

data class Resource<out T>(val status: Status, val data: T?, val errorMessage: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

inline fun <T, R> Resource<T>.mapData(transform: (T?) -> R?): Resource<R> {
    val mappedData = transform(this.data)
    return Resource(this.status, mappedData, this.errorMessage)
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}