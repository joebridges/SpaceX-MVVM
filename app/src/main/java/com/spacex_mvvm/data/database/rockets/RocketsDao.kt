package com.spacex_mvvm.data.database.rockets

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface RocketsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRocket(rocketEntity: RocketEntity)
}