package com.spacex_mvvm.features.launchlist.model

import com.spacex_mvvm.data.repositories.launches.model.Launch
import javax.inject.Inject

class LaunchListItemsMapper @Inject constructor(
    private val dateFormatter: LaunchDateFormatter
) {
    fun mapToListIem(launches: List<Launch>?): List<LaunchListItem>? {
        return launches?.map { launch ->
            with(launch) {
                LaunchListItem(
                    id,
                    missionPatchImageUrl,
                    missionName,
                    getFormattedLaunchDate(launchDateUtc, isLaunchDateTbd, isLaunchDateTentative),
                    site.siteName,
                    rocket.name
                )
            }
        }
    }

    private fun getFormattedLaunchDate(
        utcDate: String,
        isLaunchDateTbd: Boolean,
        isLaunchDateTentative: Boolean
    ): String {
        return dateFormatter.formatLaunchDate(utcDate, isLaunchDateTbd, isLaunchDateTentative)
    }
}