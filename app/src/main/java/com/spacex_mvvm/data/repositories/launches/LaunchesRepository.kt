package com.spacex_mvvm.data.repositories.launches

import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.data.repositories.launches.model.Launch
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import com.spacex_mvvm.data.repositories.launches.model.LaunchesDateOrder
import kotlinx.coroutines.flow.Flow

interface LaunchesRepository {
    fun loadLaunches(
        era: LaunchEra,
        order: LaunchesDateOrder,
        forceRefresh: Boolean
    ): Flow<Resource<List<Launch>>>

    fun loadLaunch(launchId: String): Flow<Resource<Launch>>
}