package com.spacex_mvvm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spacex_mvvm.data.database.launches.LaunchEntity
import com.spacex_mvvm.data.database.launches.images.LaunchImageEntity
import com.spacex_mvvm.data.database.launches.LaunchesDao
import com.spacex_mvvm.data.database.launches.images.LaunchImagesDao

@Database(entities = [LaunchEntity::class, LaunchImageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getLaunchesDao(): LaunchesDao
    abstract fun getLaunchImagesDao(): LaunchImagesDao
}