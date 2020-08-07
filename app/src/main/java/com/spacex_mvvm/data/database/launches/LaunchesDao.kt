package com.spacex_mvvm.data.database.launches

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LaunchesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertLaunches(launches: List<LaunchEntity>)

    @Transaction
    @Query("SELECT * FROM launches WHERE isUpcoming = :isUpcoming")
    abstract fun observeLaunches(isUpcoming: Boolean): Flow<List<LaunchEntity>>
}