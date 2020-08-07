package com.spacex_mvvm.data.mappers.launch

import com.spacex_mvvm.data.database.launches.LaunchEntity
import com.spacex_mvvm.data.mappers.EntityMapper
import com.spacex_mvvm.data.repositories.launches.model.Launch
import com.spacex_mvvm.data.repositories.launches.model.LaunchDatePrecision
import javax.inject.Inject

class LaunchEntityMapper @Inject constructor() : EntityMapper<LaunchEntity, Launch> {

    override fun mapToEntity(model: Launch): LaunchEntity {
        throw NotImplementedError()
    }

    override fun mapFromEntity(entity: LaunchEntity): Launch {
        with(entity) {
            return Launch(
                id,
                missionName,
                launchDateUtc,
                mapLaunchDatePrecision(launchDatePrecision),
                isUpcoming,
                isLaunchDateTbd,
                missionPatchImageUrl,
                rocketId,
                launchPadId
            )
        }
    }

    private fun mapLaunchDatePrecision(precision: String): LaunchDatePrecision {
        return when (precision) {
            "year" -> LaunchDatePrecision.YEAR
            "half" -> LaunchDatePrecision.HALF
            "quarter" -> LaunchDatePrecision.QUARTER
            "month" -> LaunchDatePrecision.MONTH
            "day" -> LaunchDatePrecision.DAY
            "hour" -> LaunchDatePrecision.HOUR
            else -> LaunchDatePrecision.UNKNOWN
        }
    }
}