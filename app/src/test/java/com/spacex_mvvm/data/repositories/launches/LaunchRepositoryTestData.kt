package com.spacex_mvvm.data.repositories.launches

import com.spacex_mvvm.data.database.launches.LaunchEntity
import com.spacex_mvvm.data.database.launches.LaunchWithRocketAndSite
import com.spacex_mvvm.data.database.rockets.RocketEntity
import com.spacex_mvvm.data.database.sites.SiteEntity
import com.spacex_mvvm.data.network.model.response.LaunchResponseEntity
import com.spacex_mvvm.data.network.model.response.LaunchLinksResponseEntity
import com.spacex_mvvm.data.network.model.response.RocketResponseEntity
import com.spacex_mvvm.data.network.model.response.SiteResponseEntity
import com.spacex_mvvm.data.repositories.launches.model.Launch
import com.spacex_mvvm.data.repositories.launches.model.Rocket
import com.spacex_mvvm.data.repositories.launches.model.Site

object LaunchRepositoryTestData {

    fun createLaunchEntitiesWithIds(ids: List<String>): List<LaunchWithRocketAndSite> {
        return ids.map { id ->
            val launchEntity = LaunchEntity(
                id,
                "",
                "",
                false,
                false,
                false,
                null,
                null,
                "",
                ""
            )
            val rocketEntity = RocketEntity(id, "", "")
            val siteEntity = SiteEntity(id, "")
            LaunchWithRocketAndSite(launchEntity, rocketEntity, siteEntity)
        }
    }

    fun createLaunchesWithIds(ids: List<String>): List<Launch> {
        return ids.map { id ->
            val rocket = Rocket(id, "", "")
            val site = Site(id, "")
            Launch(
                id,
                "",
                "",
                false,
                false,
                false,
                "",
                "",
                rocket,
                site
            )
        }
    }

    fun createLaunchResponseEntitiesWithIds(ids: List<String>): List<LaunchResponseEntity> {
        return ids.map { id ->
            val rocket =
                RocketResponseEntity(
                    id,
                    "",
                    ""
                )
            val site =
                SiteResponseEntity(
                    id,
                    ""
                )
            LaunchResponseEntity(
                id,
                "",
                "",
                false,
                false,
                false,
                rocket,
                site,
                LaunchLinksResponseEntity(
                    "",
                    listOf()
                )
            )
        }
    }
}