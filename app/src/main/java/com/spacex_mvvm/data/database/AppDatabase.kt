package com.spacex_mvvm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spacex_mvvm.data.database.launches.LaunchEntity
import com.spacex_mvvm.data.database.launches.LaunchesDao

@Database(entities = [LaunchEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getLaunchesDao(): LaunchesDao
}