package com.spacex_mvvm.extensions

import com.spacex_mvvm.data.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> Flow<T>.asSuccessResource(): Flow<Resource<T>> {
    return this.map { Resource.success(it) }
}

fun <T> Flow<T>.asErrorResource(errorMessage: String?): Flow<Resource<T>> {
    return this.map { Resource.error(errorMessage, it) }
}