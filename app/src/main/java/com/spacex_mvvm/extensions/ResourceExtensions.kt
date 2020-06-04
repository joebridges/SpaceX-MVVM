package com.spacex_mvvm.extensions

import com.spacex_mvvm.data.Resource

inline fun <T, R> Resource<T>.mapData(transform: (T?) -> R?): Resource<R> {
    val mappedData = transform(this.data)
    return Resource(this.status, mappedData, this.errorMessage)
}