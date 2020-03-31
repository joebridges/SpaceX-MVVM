package com.spacex_mvvm.features.launchlist.model

data class LaunchListItem(
    val id: String,
    val missionPatchImageUrl: String?,
    val missionName: String,
    val localDate: String,
    val siteName: String,
    val rocketName: String
)