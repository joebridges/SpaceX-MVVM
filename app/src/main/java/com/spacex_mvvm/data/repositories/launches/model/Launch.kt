package com.spacex_mvvm.data.repositories.launches.model

data class Launch(
    val id: String,
    val name: String,
    val details: String?,
    val launchDateUtc: String,
    val launchDatePrecision: LaunchDatePrecision,
    val isUpcoming: Boolean,
    val isLaunchDateTbd: Boolean,
    val launchImagesUrls: List<String>,
    val missionPatchImageUrl: String?,
    val rocketId: String,
    val launchPadId: String
)

enum class LaunchDatePrecision {
    YEAR, HALF, QUARTER, MONTH, DAY, HOUR, UNKNOWN
}
