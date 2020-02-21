package com.spacex_mvvm.data.network.model

import com.squareup.moshi.Json

data class Launch(
    @field:Json(name = "flight_number") val id: String,
    @field:Json(name = "mission_name") val missionName: String,
    @field:Json(name = "launch_date_utc") val launchDateUtc: String,
    val rocket: Rocket,
    @field:Json(name = "launch_site") val site: Site,
    val links: Links
)

data class Rocket(
    @field:Json(name = "rocket_id") val id: String,
    @field:Json(name = "rocket_name") val name: String,
    @field:Json(name = "rocket_type") val type: String
)

data class Site(
    @field:Json(name = "site_id") val id: String,
    @field:Json(name = "site_name") val name: String
)

data class Links(
    @field:Json(name = "mission_patch") val missionPatch: String?,
    @field:Json(name = "flickr_images") val images: List<String>
)