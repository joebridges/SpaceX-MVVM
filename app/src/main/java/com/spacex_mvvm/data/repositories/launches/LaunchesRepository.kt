package com.spacex_mvvm.data.repositories.launches

import androidx.room.withTransaction
import com.spacex_mvvm.data.database.AppDatabase
import com.spacex_mvvm.data.database.launches.LaunchesDao
import com.spacex_mvvm.data.database.rockets.RocketsDao
import com.spacex_mvvm.data.database.sites.SitesDao
import com.spacex_mvvm.data.mappers.launch.LaunchEntityMapper
import com.spacex_mvvm.data.mappers.launch.LaunchesResponseMapper
import com.spacex_mvvm.data.network.SpaceXService
import com.spacex_mvvm.data.repositories.launches.model.Launch
import com.spacex_mvvm.data.repositories.launches.model.LaunchesType
import com.spacex_mvvm.data.repositories.launches.model.toUrlPathParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LaunchesRepository @Inject constructor(
    private val spaceXService: SpaceXService,
    private val appDatabase: AppDatabase,
    private val launchesDao: LaunchesDao,
    private val rocketsDao: RocketsDao,
    private val sitesDao: SitesDao,
    private val launchEntityMapper: LaunchEntityMapper,
    private val launchesResponseMapper: LaunchesResponseMapper
) {

    suspend fun getLaunches(type: LaunchesType): List<Launch> {
        val response = spaceXService.getLaunches(type.toUrlPathParam())
        return launchesResponseMapper.mapFromResponse(response)
    }

    suspend fun storeLaunches(launches: List<Launch>) {
        val launchEntities = launches.map { launch -> launchEntityMapper.mapToEntity(launch) }
        launchEntities.forEach { entity ->
            appDatabase.withTransaction {
                launchesDao.insertLaunch(entity.launch)
                rocketsDao.insertRocket(entity.rocket)
                sitesDao.insertSite(entity.site)
            }
        }
    }

    fun observePastLaunches(): Flow<List<Launch>> {
        return launchesDao.observeLaunches().map { launchList ->
            launchList.map { launch -> launchEntityMapper.mapFromEntity(launch) }
        }
    }
}