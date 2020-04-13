package com.spacex_mvvm.data.repositories.launches

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.spacex_mvvm.data.database.launches.LaunchWithRocketAndSite
import com.spacex_mvvm.data.database.launches.LaunchesDao
import com.spacex_mvvm.data.mappers.launch.LaunchEntityMapper
import com.spacex_mvvm.data.mappers.launch.LaunchesResponseMapper
import com.spacex_mvvm.data.network.SpaceXService
import com.spacex_mvvm.data.network.model.LaunchResponseEntity
import com.spacex_mvvm.data.repositories.RateLimiter
import com.spacex_mvvm.data.repositories.launches.model.Launch
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

fun launchesRepositoryTestRobot(func: LaunchesRepositoryTestRobot.() -> Unit) =
    LaunchesRepositoryTestRobot().apply { func() }

class LaunchesRepositoryTestRobot {

    private val mockSpaceXService = mock<SpaceXService>()
    private val mockLaunchesDao = mock<LaunchesDao>()
    private val mockLaunchEntityMapper = mock<LaunchEntityMapper>()
    private val mockLaunchesResponseMapper = mock<LaunchesResponseMapper>()
    private val mockRateLimiter = mock<RateLimiter<LaunchEra>>()

    fun initialiseRepository(): LaunchesDataRepository {
        return LaunchesDataRepository(
            mockSpaceXService,
            mockLaunchesDao,
            mockLaunchEntityMapper,
            mockLaunchesResponseMapper,
            mockRateLimiter
        )
    }

    fun setWithinRateLimit(launchEra: LaunchEra) {
        whenever(mockRateLimiter.shouldFetch(launchEra)).thenReturn(false)
    }

    fun setOutsideRateLimit(launchEra: LaunchEra) {
        whenever(mockRateLimiter.shouldFetch(launchEra)).thenReturn(true)
    }

    fun mockDatabaseEntityMapper(
        expectedEntites: List<LaunchWithRocketAndSite>,
        mappedEntities: List<Launch>
    ) {
        expectedEntites.forEachIndexed { index, entity ->
            whenever(mockLaunchEntityMapper.mapFromEntity(entity)).thenReturn(mappedEntities[index])
        }
    }

    fun mockResponseMapper(
        expectedResponseEntities: List<LaunchResponseEntity>,
        launches: List<Launch>
    ) {
        whenever(mockLaunchesResponseMapper.mapFromResponse(expectedResponseEntities)).thenReturn(launches)
    }

    fun mockNetworkResponse(
        expectedLaunchEra: String,
        expectedOrder: String,
        response: List<LaunchResponseEntity>
    ) {
        runBlocking {
            whenever(mockSpaceXService.getLaunches(expectedLaunchEra, expectedOrder))
                .thenReturn(response)
        }
    }

    fun verifyLaunchesAreNotFetchedFromNetwork() = runBlocking {
        verify(mockSpaceXService, never()).getLaunches(any(), any())
    }

    fun verifyLaunchesAreFetchedFromNetwork(
        expectedEra: String,
        expectedOrder: String
    ) {
        runBlocking {  verify(mockSpaceXService).getLaunches(expectedEra, expectedOrder) }
    }

    fun returnLaunchesFromDatabaseWhenObserveLaunches(
        isUpcomingLaunches: Boolean,
        launches: List<LaunchWithRocketAndSite>
    ) {
        whenever(mockLaunchesDao.observeLaunches(isUpcomingLaunches)).thenReturn(flowOf(launches))
    }
}