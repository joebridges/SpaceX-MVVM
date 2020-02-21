package com.spacex_mvvm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spacex_mvvm.data.database.launches.LaunchEntity
import com.spacex_mvvm.data.database.launches.LaunchesDao
import com.spacex_mvvm.data.database.rockets.RocketEntity
import com.spacex_mvvm.data.database.rockets.RocketsDao
import com.spacex_mvvm.data.database.sites.SiteEntity
import com.spacex_mvvm.data.database.sites.SitesDao

@Database(entities = [LaunchEntity::class, SiteEntity::class, RocketEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getLaunchesDao(): LaunchesDao
    abstract fun getRocketsDao(): RocketsDao
    abstract fun getSitesDao(): SitesDao
}