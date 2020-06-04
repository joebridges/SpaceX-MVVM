package com.spacex_mvvm.data.repositories.launches

import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.data.repositories.launches.model.Launch
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import kotlinx.coroutines.flow.Flow

interface LaunchesRepository {
    fun loadLaunches(
        launchEra: LaunchEra,
        forceRefresh: Boolean
    ): Flow<Resource<List<Launch>>>
}