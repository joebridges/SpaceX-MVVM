package com.spacex_mvvm.features.launchlist.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.features.launchlist.model.LaunchListItem
import com.spacex_mvvm.rules.MainCoroutineRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LaunchListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `Test loading with cached data`() {
        launchListViewModelTestRobot {
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
    fun `Test loading with no cached data`() {
        launchListViewModelTestRobot {
            initialiseViewModel()
            mockLoadLaunchesResource(Resource.loading(null))

            loadLaunches()

            verifyIsLoading(true)
            verifyLaunches(null)
            verifyErrorMessage(null)
        }
    }

    @Test
    fun `Test success`() {
        launchListViewModelTestRobot {
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
    fun `Test error with cached data`() {
        launchListViewModelTestRobot {
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
    fun `Test error without cached data`() {
        launchListViewModelTestRobot {
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
                "Q4 2020"
            ),
            LaunchListItem(
                "2",
                "imageurl.com",
                "in flight abort",
                "Q4 2020"
            )
        )
    }
}