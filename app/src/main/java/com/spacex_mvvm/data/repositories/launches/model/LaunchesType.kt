package com.spacex_mvvm.data.repositories.launches.model

import com.spacex_mvvm.data.network.SpaceXService
import com.spacex_mvvm.features.launchlist.view.LaunchEra

fun LaunchEra.toUrlPathParam(): String {
    return when (this) {
        LaunchEra.PAST -> SpaceXService.PAST_LAUNCHES_PATH
        LaunchEra.UPCOMING -> SpaceXService.UPCOMING_LAUNCH_PATH
    }
}