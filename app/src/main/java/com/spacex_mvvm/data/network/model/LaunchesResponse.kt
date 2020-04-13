package com.spacex_mvvm.data.network.model

import com.squareup.moshi.Json

data class LaunchResponseEntity(
    @field:Json(name = "flight_number") val id: String,
    @field:Json(name = "mission_name") val missionName: String,
    @field:Json(name = "launch_date_utc") val launchDateUtc: String,
    @field:Json(name = "upcoming") val isUpcoming: Boolean,
    @field:Json(name = "tbd") val isLaunchDateTbd: Boolean,
    @field:Json(name = "is_tentative") val isLaunchDateTentative: Boolean,
    @field:Json(name = "rocket") val rocket: RocketResponseEntity,
    @field:Json(name = "launch_site") val site: SiteResponseEntity,
    @field:Json(name = "links") val links: LinksResponseEntity
)

data class RocketResponseEntity(
    @field:Json(name = "rocket_id") val id: String,
    @field:Json(name = "rocket_name") val name: String,
    @field:Json(name = "rocket_type") val type: String
)

data class SiteResponseEntity(
    @field:Json(name = "site_id") val id: String,
    @field:Json(name = "site_name") val name: String
)

data class LinksResponseEntity(
    @field:Json(name = "mission_patch") val missionPatch: String?,
    @field:Json(name = "flickr_images") val images: List<String>
)