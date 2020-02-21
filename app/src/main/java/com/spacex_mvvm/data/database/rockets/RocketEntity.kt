package com.spacex_mvvm.data.database.rockets

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rocket")
data class RocketEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String
)