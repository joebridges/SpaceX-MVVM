package com.spacex_mvvm.data.repositories.launches

import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.data.database.launches.LaunchesDao
import com.spacex_mvvm.data.mappers.launch.LaunchEntityMapper
import com.spacex_mvvm.data.mappers.launch.LaunchesResponseMapper
import com.spacex_mvvm.data.network.SpaceXService
import com.spacex_mvvm.data.network.model.request.LaunchesRequestOptions
import com.spacex_mvvm.data.network.model.response.LaunchesResponseEntity
import com.spacex_mvvm.data.repositories.RateLimiter
import com.spacex_mvvm.data.repositories.launches.model.Launch
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import com.spacex_mvvm.data.repositories.launches.model.LaunchesDateOrder
import com.spacex_mvvm.data.repositories.launches.model.toRequestString
import com.spacex_mvvm.extensions.asErrorResource
import com.spacex_mvvm.extensions.asSuccessResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LaunchesDataRepository @Inject constructor(
    private val spaceXService: SpaceXService,
    private val launchesDao: LaunchesDao,
    private val launchEntityMapper: LaunchEntityMapper,
    private val launchesResponseMapper: LaunchesResponseMapper,
    private val launchesRateLimiter: RateLimiter<LaunchEra>
) : LaunchesRepository {

    override fun loadLaunches(
        era: LaunchEra,
        order: LaunchesDateOrder,
        forceRefresh: Boolean
    ): Flow<Resource<List<Launch>>> {
        val launches = observeLaunches(era)
        return if (shouldFetch(forceRefresh, era)) {
            updateLaunchesFromNetwork(launches, era, order)
        } else {
            launches.asSuccessResource()
        }
    }

    private fun updateLaunchesFromNetwork(
        launches: Flow<List<Launch>>,
        launchEra: LaunchEra,
        order: LaunchesDateOrder
    ): Flow<Resource<List<Launch>>> {
        return flow {
            emit(Resource.loading(launches.first()))
            try {
                val launchesResponse = fetchLaunches(launchEra, order)
                val launchEntities = launchesResponseMapper.mapFromResponse(launchesResponse.launches)
                launchesDao.insertLaunches(launchEntities)
                emitAll(launches.asSuccessResource())
            } catch (e: Exception) {
                emitAll(launches.asErrorResource(e.message))
            }
        }
    }

    private fun shouldFetch(forceRefresh: Boolean, launchEra: LaunchEra) =
        forceRefresh || launchesRateLimiter.shouldFetch(launchEra)

    private suspend fun fetchLaunches(
        era: LaunchEra,
        orderBy: LaunchesDateOrder
    ): LaunchesResponseEntity {
        return spaceXService.getLaunches(
            LaunchesRequestOptions.create(
                upcoming = era == LaunchEra.UPCOMING,
                orderBy = orderBy.toRequestString()
            )
        )
    }

    private fun observeLaunches(
        launchEra: LaunchEra
    ): Flow<List<Launch>> {
        return launchesDao.observeLaunches(launchEra == LaunchEra.UPCOMING)
            .map { launchEntities ->
                launchEntities.map { launchEntity ->
                    launchEntityMapper.mapFromEntity(launchEntity)
                }
            }
    }
}