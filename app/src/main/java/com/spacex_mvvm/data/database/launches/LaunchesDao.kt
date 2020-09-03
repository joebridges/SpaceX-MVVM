package com.spacex_mvvm.data.database.launches

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.withTransaction
import com.spacex_mvvm.data.database.AppDatabase
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LaunchesDao constructor(
    private val appDatabase: AppDatabase
) {

    suspend fun insertLaunches(launchesWithImages: List<LaunchWithImagesEntity>) {
        val launchesEntities =
            launchesWithImages.map { launchWithImages -> launchWithImages.launch }
        val launchImages =
            launchesWithImages.mapNotNull { launchWithImages -> launchWithImages.images }.flatten()
        appDatabase.withTransaction {
            insertLaunchEntities(launchesEntities)
            appDatabase.getLaunchImagesDao().insertLaunchImages(launchImages)
        }
    }

    @Transaction
    @Query("SELECT * FROM launches WHERE isUpcoming = :isUpcoming")
    abstract fun observeLaunches(isUpcoming: Boolean): Flow<List<LaunchWithImagesEntity>>

    @Transaction
    @Query("SELECT * FROM launches WHERE id =:id")
    abstract fun observeLaunch(id: String): Flow<LaunchWithImagesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertLaunchEntities(launches: List<LaunchEntity>)
}