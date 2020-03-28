package com.spacex_mvvm.data.database.launches

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunch(launch: LaunchEntity)

    @Query("SELECT * FROM launches WHERE isUpcoming = :isUpcoming")
    fun observeLaunches(isUpcoming: Boolean): Flow<List<LaunchWithRocketAndSite>>
}