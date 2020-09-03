package com.spacex_mvvm.data.database.launches.images

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface LaunchImagesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLaunchImages(launchImages: List<LaunchImageEntity>)
}