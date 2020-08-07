package com.spacex_mvvm.data.repositories.launches

import com.spacex_mvvm.data.database.launches.LaunchEntity
import com.spacex_mvvm.data.network.model.response.LaunchLinksResponseEntity
import com.spacex_mvvm.data.network.model.response.LaunchResponseEntity
import com.spacex_mvvm.data.network.model.response.LaunchesResponseEntity
import com.spacex_mvvm.data.network.model.response.PatchLinksResponseEntity
import com.spacex_mvvm.data.repositories.launches.model.Launch
import com.spacex_mvvm.data.repositories.launches.model.LaunchDatePrecision

object LaunchRepositoryTestData {

    fun createLaunchEntitiesWithIds(ids: List<String>): List<LaunchEntity> {
        return ids.map { id ->
            LaunchEntity(
                id,
                "",
                "",
                "hour",
                false,
                false,
                null,
                "",
                ""
            )
        }
    }

    fun createLaunchesWithIds(ids: List<String>): List<Launch> {
        return ids.map { id ->
            Launch(
                id,
                "",
                "",
                LaunchDatePrecision.HOUR,
                false,
                false,
                "",
                "",
                ""
            )
        }
    }

    fun createLaunchResponseEntitiesWithIds(ids: List<String>): LaunchesResponseEntity {
        val launches = ids.map { id ->
            LaunchResponseEntity(
                id,
                "",
                "",
                "hour",
                false,
                false,
                LaunchLinksResponseEntity(PatchLinksResponseEntity("", "")),
                "",
                ""
            )
        }
        return LaunchesResponseEntity(launches)
    }
}