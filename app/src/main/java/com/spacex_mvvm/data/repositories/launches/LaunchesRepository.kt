package com.spacex_mvvm.data.repositories.launches

import androidx.room.withTransaction
import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.data.database.AppDatabase
import com.spacex_mvvm.data.database.launches.LaunchesDao
import com.spacex_mvvm.data.database.rockets.RocketsDao
import com.spacex_mvvm.data.database.sites.SitesDao
import com.spacex_mvvm.data.mappers.launch.LaunchEntityMapper
import com.spacex_mvvm.data.mappers.launch.LaunchesResponseMapper
import com.spacex_mvvm.data.network.SpaceXService
import com.spacex_mvvm.data.repositories.launches.model.Launch
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import com.spacex_mvvm.data.repositories.launches.model.getDefaultOrder
import com.spacex_mvvm.data.repositories.launches.model.toUrlPathParam
import com.spacex_mvvm.extensions.asErrorResource
import com.spacex_mvvm.extensions.asSuccessResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LaunchesRepository @Inject constructor(
    private val spaceXService: SpaceXService,
    private val appDatabase: AppDatabase,
    private val launchesDao: LaunchesDao,
    private val rocketsDao: RocketsDao,
    private val sitesDao: SitesDao,
    private val launchEntityMapper: LaunchEntityMapper,
    private val launchesResponseMapper: LaunchesResponseMapper
) {

    private val launchesRateLimiter = RateLimiter<LaunchEra>(10, TimeUnit.MINUTES)

    fun loadLaunches(
        launchEra: LaunchEra,
        forceRefresh: Boolean = false
    ): Flow<Resource<List<Launch>>> {
        val launches = observeLaunches(launchEra)
        return if (shouldFetch(forceRefresh, launchEra)) {
            updateLaunchesFromNetwork(launches, launchEra)
        } else {
            launches.asSuccessResource()
        }
    }

    private fun updateLaunchesFromNetwork(
        launches: Flow<List<Launch>>,
        launchEra: LaunchEra
    ): Flow<Resource<List<Launch>>> {
        return flow {
            emit(Resource.loading(launches.first()))
            try {
                val remoteLaunches = fetchLaunches(launchEra)
                storeLaunches(remoteLaunches)
                emitAll(launches.asSuccessResource())
            } catch (e: Exception) {
                emitAll(launches.asErrorResource(e.message))
            }
        }
    }

    private fun shouldFetch(forceRefresh: Boolean, launchEra: LaunchEra) =
        forceRefresh || launchesRateLimiter.shouldFetch(launchEra)

    private suspend fun fetchLaunches(era: LaunchEra): List<Launch> {
        val response = spaceXService.getLaunches(
            era.toUrlPathParam(),
            era.getDefaultOrder()
        )
        return launchesResponseMapper.mapFromResponse(response)
    }

    private suspend fun storeLaunches(launches: List<Launch>) {
        val launchEntities = launches.map { launch -> launchEntityMapper.mapToEntity(launch) }
        launchEntities.forEach { entity ->
            appDatabase.withTransaction {
                launchesDao.insertLaunch(entity.launch)
                rocketsDao.insertRocket(entity.rocket)
                sitesDao.insertSite(entity.site)
            }
        }
    }

    private fun observeLaunches(launchEra: LaunchEra): Flow<List<Launch>> {
        return launchesDao.observeLaunches(launchEra == LaunchEra.UPCOMING).map { launchList ->
            launchList.map { launch -> launchEntityMapper.mapFromEntity(launch) }
        }
    }
}