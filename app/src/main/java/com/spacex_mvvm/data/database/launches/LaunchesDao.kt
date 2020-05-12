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
abstract class LaunchesDao(
    private val appDatabase: AppDatabase
) {
    suspend fun insertLaunch(
        entity: LaunchWithRocketAndSite
    ) = appDatabase.withTransaction {
        insertLaunch(entity.launch)
        appDatabase.getRocketsDao().insertRocket(entity.rocket)
        appDatabase.getSitesDao().insertSite(entity.site)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertLaunch(launch: LaunchEntity)

    @Transaction
    @Query("SELECT * FROM launches WHERE isUpcoming = :isUpcoming")
    abstract fun observeLaunches(isUpcoming: Boolean): Flow<List<LaunchWithRocketAndSite>>
}