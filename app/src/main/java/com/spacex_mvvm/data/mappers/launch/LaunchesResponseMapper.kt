package com.spacex_mvvm.data.mappers.launch

import com.spacex_mvvm.data.mappers.NetworkResponseMapper
import com.spacex_mvvm.data.repositories.launches.model.Launch
import com.spacex_mvvm.data.repositories.launches.model.Rocket
import com.spacex_mvvm.data.repositories.launches.model.Site
import javax.inject.Inject
import com.spacex_mvvm.data.network.model.Launch as NetworkLaunch
import com.spacex_mvvm.data.network.model.Rocket as NetworkRocket
import com.spacex_mvvm.data.network.model.Site as NetworkSite

class LaunchesResponseMapper @Inject constructor() :
    NetworkResponseMapper<List<NetworkLaunch>, List<Launch>> {

    override fun mapFromResponse(response: List<NetworkLaunch>): List<Launch> {
        return response.map { launch ->
            with(launch) {
                Launch(
                    id,
                    missionName,
                    launchDateUtc,
                    isUpcoming,
                    isLaunchDateTbd,
                    isLaunchDateTentative,
                    links.missionPatch,
                    getLaunchImageUrl(launch),
                    mapRocket(rocket),
                    mapSite(site)
                )
            }
        }
    }

    private fun mapRocket(rocket: NetworkRocket): Rocket {
        return with(rocket) {
            Rocket(id, name, type)
        }
    }

    private fun mapSite(site: NetworkSite): Site {
        return with(site) {
            Site(id, name)
        }
    }

    private fun getLaunchImageUrl(launch: NetworkLaunch): String? {
        return launch.links.images.firstOrNull()
    }
}