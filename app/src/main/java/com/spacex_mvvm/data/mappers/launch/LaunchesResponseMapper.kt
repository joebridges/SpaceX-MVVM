package com.spacex_mvvm.data.mappers.launch

import com.spacex_mvvm.data.database.launches.LaunchEntity
import com.spacex_mvvm.data.mappers.NetworkResponseMapper
import com.spacex_mvvm.data.network.model.response.LaunchResponseEntity
import javax.inject.Inject

class LaunchesResponseMapper @Inject constructor() :
    NetworkResponseMapper<List<LaunchResponseEntity>, List<LaunchEntity>> {

    override fun mapFromResponse(response: List<LaunchResponseEntity>): List<LaunchEntity> {
        return response.map { launch ->
            with(launch) {
                LaunchEntity(
                    id,
                    missionName,
                    launchDateUtc,
                    launchDatePrecision,
                    isUpcoming,
                    isLaunchDateTbd,
                    links.patch.small,
                    rocketId,
                    launchpadId
                )
            }
        }
    }
}