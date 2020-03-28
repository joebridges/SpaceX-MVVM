package com.spacex_mvvm.data.mappers.launch

import com.spacex_mvvm.data.database.launches.LaunchEntity
import com.spacex_mvvm.data.database.launches.LaunchWithRocketAndSite
import com.spacex_mvvm.data.mappers.EntityMapper
import com.spacex_mvvm.data.mappers.rocket.RocketEntityMapper
import com.spacex_mvvm.data.mappers.site.SiteEntityMapper
import com.spacex_mvvm.data.repositories.launches.model.Launch
import javax.inject.Inject

class LaunchEntityMapper @Inject constructor(
    private val rocketMapper: RocketEntityMapper,
    private val siteMapper: SiteEntityMapper
) : EntityMapper<LaunchWithRocketAndSite, Launch> {

    override fun mapToEntity(model: Launch): LaunchWithRocketAndSite {
        val launchEntity = with(model) {
            LaunchEntity(
                id,
                missionName,
                launchDateUtc,
                isUpcoming,
                missionPatchImageUrl,
                launchImageUrl,
                rocket.id,
                site.id
            )
        }
        val rocketEntity = rocketMapper.mapToEntity(model.rocket)
        val siteEntity = siteMapper.mapToEntity(model.site)
        return LaunchWithRocketAndSite(
            launchEntity,
            rocketEntity,
            siteEntity
        )
    }

    override fun mapFromEntity(entity: LaunchWithRocketAndSite): Launch {
        val rocket = rocketMapper.mapFromEntity(entity.rocket)
        val site = siteMapper.mapFromEntity(entity.site)
        return with(entity.launch) {
            Launch(
                id,
                missionName,
                launchDateUtc,
                isUpcoming,
                missionPatchImageUrl,
                launchImageUrl,
                rocket,
                site
            )
        }
    }
}