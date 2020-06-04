package com.spacex_mvvm.data.repositories.launches

import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class LaunchesRepositoryTest {

    @Test
    fun `Does not fetch from network when within rate limit and force refresh is false`() {
        launchesRepositoryTestRobot {
            setWithinRateLimit(LaunchEra.UPCOMING)
            val launchEntities = LaunchRepositoryTestData.createLaunchEntitiesWithIds(
                listOf("101", "102")
            )
            returnLaunchesFromDatabaseWhenObserveLaunches(true, launchEntities)
            val launches = LaunchRepositoryTestData.createLaunchesWithIds(listOf("101", "102"))
            mockDatabaseEntityMapper(launchEntities, launches)

            val repository = initialiseRepository()
            val result = runBlocking { repository.loadLaunches(LaunchEra.UPCOMING, false).toList() }

            verifyLaunchesAreNotFetchedFromNetwork()
            val expectedResult = listOf(Resource.success(launches))
            Assert.assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `Fetch from network when outside of rate limit`() {
        launchesRepositoryTestRobot {
            setOutsideRateLimit(LaunchEra.UPCOMING)
            val launchEntities = LaunchRepositoryTestData.createLaunchEntitiesWithIds(listOf("101", "102"))
            returnLaunchesFromDatabaseWhenObserveLaunches(true, launchEntities)
            val launches = LaunchRepositoryTestData.createLaunchesWithIds(listOf("101", "102"))
            mockDatabaseEntityMapper(launchEntities, launches)
            val launchesResponse = LaunchRepositoryTestData.createLaunchResponseEntitiesWithIds(listOf("101", "102"))
            mockNetworkResponse("upcoming", "asc", launchesResponse)
            mockResponseMapper(launchesResponse, launches)

            val repository = initialiseRepository()
            val result = runBlocking { repository.loadLaunches(LaunchEra.UPCOMING, false).toList() }

            verifyLaunchesAreFetchedFromNetwork("upcoming", "asc")
            val expectedResult = listOf(Resource.loading(launches), Resource.success(launches))
            Assert.assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `Fetch from network when in rate limit and force refresh`() {
        launchesRepositoryTestRobot {
            setWithinRateLimit(LaunchEra.UPCOMING)
            val launchEntities = LaunchRepositoryTestData.createLaunchEntitiesWithIds(listOf("101", "102"))
            returnLaunchesFromDatabaseWhenObserveLaunches(true, launchEntities)
            val launches = LaunchRepositoryTestData.createLaunchesWithIds(listOf("101", "102"))
            mockDatabaseEntityMapper(launchEntities, launches)
            val launchesResponse = LaunchRepositoryTestData.createLaunchResponseEntitiesWithIds(listOf("101", "102"))
            mockNetworkResponse("upcoming", "asc", launchesResponse)
            mockResponseMapper(launchesResponse, launches)

            val repository = initialiseRepository()
            val result = runBlocking { repository.loadLaunches(LaunchEra.UPCOMING, true).toList() }

            verifyLaunchesAreFetchedFromNetwork("upcoming", "asc")
            val expectedResult = listOf(Resource.loading(launches), Resource.success(launches))
            Assert.assertEquals(expectedResult, result)
        }
    }
}