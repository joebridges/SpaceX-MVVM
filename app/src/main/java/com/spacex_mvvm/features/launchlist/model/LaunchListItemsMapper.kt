package com.spacex_mvvm.features.launchlist.model

import com.spacex_mvvm.data.repositories.launches.model.Launch
import javax.inject.Inject

class LaunchListItemsMapper @Inject constructor(
    private val dateFormatter: LaunchDateFormatter
) {
    fun mapToListItems(launches: List<Launch>?): List<LaunchListItem> {
        return launches?.map { launch ->
            with(launch) {
                LaunchListItem(
                    id,
                    missionPatchImageUrl,
                    name,
                    dateFormatter.formatLaunchDate(launchDateUtc, launchDatePrecision)
                )
            }
        } ?: emptyList()
    }
}