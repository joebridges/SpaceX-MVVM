package com.spacex_mvvm.data.database.launches

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launches")
data class LaunchEntity(
    @PrimaryKey val id: String,
    val missionName: String,
    val launchDateUtc: String,
    val missionPatchImageUrl: String?,
    val launchImageUrl: String?,
    val rocketId: String,
    val siteId: String
)
