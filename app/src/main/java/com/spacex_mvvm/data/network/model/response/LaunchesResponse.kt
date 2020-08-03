package com.spacex_mvvm.data.network.model.response

import com.squareup.moshi.Json

data class LaunchesResponseEntity(
    @field:Json(name = "docs") val launches: List<LaunchResponseEntity>
)

data class LaunchResponseEntity(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val missionName: String,
    @field:Json(name = "date_utc") val launchDateUtc: String,
    @field:Json(name = "date_precision") val launchDatePrecision: String,
    @field:Json(name = "upcoming") val isUpcoming: Boolean,
    @field:Json(name = "tbd") val isLaunchDateTbd: Boolean,
    @field:Json(name = "links") val links: LaunchLinksResponseEntity,
    @field:Json(name = "rocket") val rocketId: String,
    @field:Json(name = "launchpad") val launchpadId: String
)

data class LaunchLinksResponseEntity(
    @field:Json(name = "patch") val patch: PatchLinksResponseEntity
)

data class PatchLinksResponseEntity(
    @field:Json(name = "small") val small: String?,
    @field:Json(name = "large") val large: String?
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