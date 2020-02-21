package com.spacex_mvvm.data.repositories.launches.model

data class Launch(
    val id: String,
    val missionName: String,
    val launchDateUtc: String,
    val missionPatchImageUrl: String?,
    val launchImageUrl: String?,
    val rocket: Rocket,
    val site: Site
)
