package com.spacex_mvvm.test.features.launchlist

import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import com.spacex_mvvm.features.launchlist.model.LaunchListItem
import org.junit.Test

class LaunchListTests {

    @Test
    fun testSwipeToRefresh() {
        launchListRobot {
            initialiseMockViewModel()
            launchFragment("past")
            verifyLoadLaunchesIsCalled(LaunchEra.PAST, false)

            swipeToRefresh()

            verifyLoadLaunchesIsCalled(LaunchEra.PAST, true)
        }
    }

    @Test
    fun testIsLoading() {
        launchListRobot {
            initialiseMockViewModel()
            launchFragment("past")

            setIsLoading(true)

            verifyIsRefreshing()
            verifyRecyclerViewIsEmpty()
            verifySnackbarIsNotVisible()
        }
    }

    @Test
    fun testIsNotLoading() {
        launchListRobot {
            initialiseMockViewModel()
            launchFragment("past")

            setIsLoading(false)

            verifyIsNotRefreshing()
            verifyRecyclerViewIsEmpty()
            verifySnackbarIsNotVisible()
        }
    }

    @Test
    fun testErrorMessage() {
        launchListRobot {
            initialiseMockViewModel()
            launchFragment("past")

            setErrorMessage("An unexpected error occurred")

            verifyIsNotRefreshing()
            verifyRecyclerViewIsEmpty()
            verifySnackbarText("An unexpected error occurred")
        }
    }

    @Test
    fun testNoErrorMessage() {
        launchListRobot {
            initialiseMockViewModel()
            launchFragment("past")

            setErrorMessage(null)

            verifyIsNotRefreshing()
            verifyRecyclerViewIsEmpty()
            verifySnackbarIsNotVisible()
        }
    }

    @Test
    fun testLaunchDisplayed() {
        launchListRobot {
            initialiseMockViewModel()
            launchFragment("past")

            setData(
                listOf(
                    LaunchListItem(
                        "1",
                        null,
                        "Starlink 6",
                        "12th May 2020",
                        "KSC LC 39A",
                        "Falcon 9"
                    )
                )
            )

            verifyIsNotRefreshing()
            verifySnackbarIsNotVisible()
            verifyLaunchMissionName("Starlink 6", 0)
            verifyLaunchDate("12th May 2020", 0)
            verifySiteName("KSC LC 39A", 0)
            verifyRocketName("Falcon 9", 0)
        }
    }
}