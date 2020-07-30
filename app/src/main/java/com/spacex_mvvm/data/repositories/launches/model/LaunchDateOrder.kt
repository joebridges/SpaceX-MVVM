package com.spacex_mvvm.data.repositories.launches.model

enum class LaunchesDateOrder {
    ASCENDING, DESCENDING
}

fun LaunchesDateOrder.toRequestString(): String {
    return when(this) {
        LaunchesDateOrder.ASCENDING -> "ASC"
        LaunchesDateOrder.DESCENDING -> "DESC"
    }
}
