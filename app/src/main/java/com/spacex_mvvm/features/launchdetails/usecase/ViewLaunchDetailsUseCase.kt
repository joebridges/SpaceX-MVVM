package com.spacex_mvvm.features.launchdetails.usecase

import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.data.repositories.launches.LaunchesRepository
import com.spacex_mvvm.extensions.mapData
import com.spacex_mvvm.features.launchdetails.model.LaunchDetailMapper
import com.spacex_mvvm.features.launchdetails.model.LaunchDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ViewLaunchDetailsUseCase @Inject constructor(
    private val launchesRepository: LaunchesRepository,
    private val launchDetailMapper: LaunchDetailMapper
) {

    fun loadLaunch(launchId: String): Flow<Resource<LaunchDetails>> {
        return launchesRepository.loadLaunch(launchId).map { resource ->
            resource.mapData { launch ->
                launchDetailMapper.mapToLaunchDetails(launch)
            }
        }
    }
}