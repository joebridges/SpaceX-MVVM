package com.spacex_mvvm.data.repositories.launches.model

import com.spacex_mvvm.data.network.SpaceXService

enum class LaunchEra {
    PAST, UPCOMING
}

fun LaunchEra.toUrlPathParam(): String {
    return when (this) {
        LaunchEra.PAST -> SpaceXService.PAST_LAUNCHES_PATH
        LaunchEra.UPCOMING -> SpaceXService.UPCOMING_LAUNCH_PATH
    }
}

fun LaunchEra.getDefaultOrder(): String {
    return when (this) {
        LaunchEra.PAST -> SpaceXService.ORDER_DESC
        LaunchEra.UPCOMING -> SpaceXService.ORDER_ASC
    }
}