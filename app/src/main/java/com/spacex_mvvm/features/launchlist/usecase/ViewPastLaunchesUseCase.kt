package com.spacex_mvvm.features.launchlist.usecase

import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.data.repositories.launches.LaunchesRepository
import com.spacex_mvvm.data.repositories.launches.model.LaunchesType
import com.spacex_mvvm.extensions.asErrorResource
import com.spacex_mvvm.extensions.asSuccessResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ViewPastLaunchesUseCase @Inject constructor(
    private val repository: LaunchesRepository,
    private val launchListItemsMapper: LaunchListItemsMapper
) {
    fun loadPastLaunches(): Flow<Resource<List<LaunchListItem>>> {
        val launches = repository.observePastLaunches().map { launches ->
            launchListItemsMapper.mapToListIem(launches)
        }

        return flow {
            emit(Resource.loading(launches.first()))
            try {
                val remoteLaunches = repository.getLaunches(LaunchesType.PAST)
                repository.storeLaunches(remoteLaunches)
                emitAll(launches.asSuccessResource())
            } catch (e: Exception) {
                emitAll(launches.asErrorResource(e.message))
            }
        }
    }
}