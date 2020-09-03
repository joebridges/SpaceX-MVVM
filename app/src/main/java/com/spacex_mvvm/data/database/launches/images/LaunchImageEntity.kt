package com.spacex_mvvm.data.database.launches.images

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launchImage")
data class LaunchImageEntity(
    @PrimaryKey val imageUrl: String,
    val launchId: String
)