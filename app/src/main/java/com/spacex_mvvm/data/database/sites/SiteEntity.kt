package com.spacex_mvvm.data.database.sites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "site")
data class SiteEntity(
    @PrimaryKey val id: String,
    val siteName: String
)