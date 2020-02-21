package com.spacex_mvvm.data.database.launches

import androidx.room.Embedded
import androidx.room.Relation
import com.spacex_mvvm.data.database.rockets.RocketEntity
import com.spacex_mvvm.data.database.sites.SiteEntity

data class LaunchWithRocketAndSite(
    @Embedded val launch: LaunchEntity,
    @Relation(parentColumn = "rocketId", entityColumn = "id") val rocket: RocketEntity,
    @Relation(parentColumn = "siteId", entityColumn = "id") val site: SiteEntity
)