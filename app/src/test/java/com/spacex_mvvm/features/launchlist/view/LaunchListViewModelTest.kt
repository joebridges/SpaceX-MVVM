package com.spacex_mvvm.features.launchlist.view

import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.features.launchlist.model.LaunchListItem
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.spacex_mvvm.rules.MainCoroutineRule
import org.junit.rules.TestRule
import org.junit.Rule

class LaunchListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun testLoadingWithCachedData() {
        launchListViewModelRobot {
            initialiseViewModel()
            val launches = createListOfLaunches()
            mockLoadLaunchesResource(Resource.loading(launches))

            loadLaunches()

            verifyIsLoading(true)
            verifyLaunches(launches)
            verifyErrorMessage(null)
        }
    }

    @Test
    fun testLoadingWithoutCachedData() {
        launchListViewModelRobot {
            initialiseViewModel()
            mockLoadLaunchesResource(Resource.loading(null))

            loadLaunches()

            verifyIsLoading(true)
            verifyLaunches(null)
            verifyErrorMessage(null)
        }
    }

    @Test
    fun testSuccess() {
        launchListViewModelRobot {
            initialiseViewModel()
            val launches = createListOfLaunches()
            mockLoadLaunchesResource(Resource.success(launches))

            loadLaunches()

            verifyIsLoading(false)
            verifyErrorMessage(null)
            verifyLaunches(launches)
        }
    }

    @Test
    fun testFailureWithCachedData() {
        launchListViewModelRobot {
            initialiseViewModel()
            val launches = createListOfLaunches()
            mockLoadLaunchesResource(Resource.error("An error occurred", launches))

            loadLaunches()

            verifyIsLoading(false)
            verifyErrorMessage("An error occurred")
            verifyLaunches(launches)
        }
    }

    @Test
    fun testFailureWithoutCachedData() {
        launchListViewModelRobot {
            initialiseViewModel()
            mockLoadLaunchesResource(Resource.error("An error occurred", null))

            loadLaunches()

            verifyIsLoading(false)
            verifyErrorMessage("An error occurred")
            verifyLaunches(null)
        }
    }

    private fun createListOfLaunches(): List<LaunchListItem> {
        return listOf(
            LaunchListItem(
                "1",
                "imageurl.com",
                "starlink",
                "",
                "KSC LC 39A",
                "Falcon 9"
            ),
            LaunchListItem(
                "2",
                "imageurl.com",
                "in flight abort",
                "",
                "KSC LC 39A",
                "Falcon 9"
            )
        )
    }
}