package com.spacex_mvvm.features.launchlist.model

data class LaunchListItem(
    val id: String,
    val missionPatchImageUrl: String?,
    val name: String,
    val localDate: String
)