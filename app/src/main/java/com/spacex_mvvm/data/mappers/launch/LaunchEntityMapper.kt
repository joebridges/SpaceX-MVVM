package com.spacex_mvvm.data.mappers.launch

import com.spacex_mvvm.data.database.launches.LaunchWithImagesEntity
import com.spacex_mvvm.data.database.launches.images.LaunchImageEntity
import com.spacex_mvvm.data.mappers.EntityMapper
import com.spacex_mvvm.data.repositories.launches.model.Launch
import com.spacex_mvvm.data.repositories.launches.model.LaunchDatePrecision
import javax.inject.Inject

class LaunchEntityMapper @Inject constructor() : EntityMapper<LaunchWithImagesEntity, Launch> {

    override fun mapToEntity(model: Launch): LaunchWithImagesEntity {
        throw NotImplementedError()
    }

    override fun mapFromEntity(entity: LaunchWithImagesEntity): Launch {
        with(entity.launch) {
            return Launch(
                id,
                name,
                details,
                launchDateUtc,
                mapLaunchDatePrecision(launchDatePrecision),
                isUpcoming,
                isLaunchDateTbd,
                mapLaunchImages(entity.images),
                missionPatchImageUrl,
                rocketId,
                launchPadId
            )
        }
    }

    private fun mapLaunchImages(launchImageEntities: List<LaunchImageEntity>?): List<String> {
        return launchImageEntities?.map { launchImageEntity -> launchImageEntity.imageUrl }
            ?: emptyList()
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