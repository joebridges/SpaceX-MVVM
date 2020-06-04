package com.spacex_mvvm.data.database.sites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface SitesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSite(site: SiteEntity)
}