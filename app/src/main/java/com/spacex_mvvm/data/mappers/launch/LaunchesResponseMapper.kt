package com.spacex_mvvm.data.mappers.launch

import com.spacex_mvvm.data.database.launches.LaunchEntity
import com.spacex_mvvm.data.database.launches.images.LaunchImageEntity
import com.spacex_mvvm.data.database.launches.LaunchWithImagesEntity
import com.spacex_mvvm.data.mappers.NetworkResponseMapper
import com.spacex_mvvm.data.network.model.response.FlickrLinksResponseEntity
import com.spacex_mvvm.data.network.model.response.LaunchResponseEntity
import javax.inject.Inject

class LaunchesResponseMapper @Inject constructor() :
    NetworkResponseMapper<List<LaunchResponseEntity>, List<LaunchWithImagesEntity>> {

    override fun mapFromResponse(response: List<LaunchResponseEntity>): List<LaunchWithImagesEntity> {
        return response.map { launch ->
            with(launch) {
                LaunchWithImagesEntity(
                    LaunchEntity(
                        id,
                        name,
                        details,
                        launchDateUtc,
                        launchDatePrecision,
                        isUpcoming,
                        isLaunchDateTbd,
                        links.patch.small,
                        rocketId,
                        launchpadId
                    ),
                    mapImageLinksToEntities(id, links.launchImages)
                )
            }
        }
    }

    fun mapImageLinksToEntities(
        launchId: String,
        links: FlickrLinksResponseEntity
    ): List<LaunchImageEntity> {
        return links.original.map { url ->
            LaunchImageEntity(
                url,
                launchId
            )
        }
    }
}