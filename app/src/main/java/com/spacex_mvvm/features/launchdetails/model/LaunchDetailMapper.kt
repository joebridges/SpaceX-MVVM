package com.spacex_mvvm.features.launchdetails.model

import com.spacex_mvvm.data.repositories.launches.model.Launch
import javax.inject.Inject

class LaunchDetailMapper @Inject constructor() {
    fun mapToLaunchDetails(launch: Launch?): LaunchDetails? {
        return if (launch != null) {
            LaunchDetails(
                launch.launchImagesUrls.firstOrNull(), // TODO only show one image for now. Improve to display all
                launch.missionPatchImageUrl,
                launch.name,
                launch.details
            )
        } else null
    }
}