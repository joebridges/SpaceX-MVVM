package com.spacex_mvvm.data.mappers

interface NetworkResponseMapper<NetworkResponse, Model> {
    fun mapFromResponse(response: NetworkResponse): Model
}