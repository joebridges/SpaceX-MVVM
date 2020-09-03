package com.spacex_mvvm.data.database.launches

import androidx.room.Embedded
import androidx.room.Relation
import com.spacex_mvvm.data.database.launches.images.LaunchImageEntity

data class LaunchWithImagesEntity(
    @Embedded val launch: LaunchEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "launchId"
    ) val images: List<LaunchImageEntity>?
)