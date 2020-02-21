package com.spacex_mvvm.data.repositories.launches.model

import com.spacex_mvvm.data.network.SpaceXService

enum class LaunchesType {
    PAST, FUTURE
}

fun LaunchesType.toUrlPathParam(): String {
    return when(this) {
        LaunchesType.PAST -> SpaceXService.PAST_LAUNCHES_PATH
        LaunchesType.FUTURE -> SpaceXService.FUTURE_LAUNCH_PATH
    }
}