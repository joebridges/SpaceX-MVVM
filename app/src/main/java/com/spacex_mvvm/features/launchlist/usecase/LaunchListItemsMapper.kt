package com.spacex_mvvm.features.launchlist.usecase

import com.spacex_mvvm.data.repositories.launches.model.Launch
import javax.inject.Inject

class LaunchListItemsMapper @Inject constructor(
    private val dateConverter: UtcToLocalDateConverter
) {
    fun mapToListIem(launches: List<Launch>): List<LaunchListItem> {
        return launches.map { launch ->
            with(launch) {
                LaunchListItem(
                    id,
                    missionPatchImageUrl,
                    missionName,
                    mapUtcDateToLocal(launchDateUtc),
                    site.siteName,
                    rocket.name
                )
            }
        }
    }

    private fun mapUtcDateToLocal(utcDate: String): String {
        return dateConverter.convertToLocalShortDateString(utcDate)
    }
}