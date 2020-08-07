package com.spacex_mvvm.test.features.launchlist

import android.os.Bundle
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.data.repositories.di.RepositoriesModule
import com.spacex_mvvm.data.repositories.launches.LaunchesRepository
import com.spacex_mvvm.data.repositories.launches.model.Launch
import com.spacex_mvvm.data.repositories.launches.model.LaunchDatePrecision
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import com.spacex_mvvm.data.repositories.launches.model.LaunchesDateOrder
import com.spacex_mvvm.features.launchlist.view.LaunchListFragment
import com.spacex_mvvm.test.HiltEmptyFragmentActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(RepositoriesModule::class)
class LaunchListTests {

    /**
     * Unable to use the launchFragmentInContainer method provided by the fragment-testing library
     * due to the requirement of the Activity having an @AndroidEntryPoint annotation for Dagger Hilt
     * handling this manually until a better solution is available
     */
    @get:Rule
    val activityRule = ActivityTestRule(HiltEmptyFragmentActivity::class.java, false, false)

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val mockRepository: LaunchesRepository = mock()

    @Test
    fun testSwipeToRefresh() {
        returnDataFromRepository(
            expectedLaunchEra = LaunchEra.UPCOMING,
            expectedOrder = LaunchesDateOrder.ASCENDING,
            expectedForceRefresh = false,
            dataToReturn = Resource.success(emptyList())
        )

        returnDataFromRepository(
            expectedLaunchEra = LaunchEra.UPCOMING,
            expectedOrder = LaunchesDateOrder.ASCENDING,
            expectedForceRefresh = true,
            dataToReturn = Resource.loading(emptyList())
        )

        startFragmentWithEra("upcoming")

        launchListRobot {
            verifyIsNotRefreshing()
            swipeToRefresh()
            verifyIsRefreshing()
        }
    }

    @Test
    fun testIsLoading() {
        returnDataFromRepository(
            expectedLaunchEra = LaunchEra.UPCOMING,
            expectedOrder = LaunchesDateOrder.ASCENDING,
            expectedForceRefresh = false,
            dataToReturn = Resource.loading(null)
        )

        startFragmentWithEra("upcoming")

        launchListRobot {
            verifyIsRefreshing()
            verifyRecyclerViewIsEmpty()
            verifySnackbarIsNotVisible()
        }
    }

    @Test
    fun testIsLoadingWithData() {
        returnDataFromRepository(
            expectedLaunchEra = LaunchEra.UPCOMING,
            expectedOrder = LaunchesDateOrder.ASCENDING,
            expectedForceRefresh = false,
            dataToReturn = Resource.loading(
                listOf(
                    createLaunch(
                        missionName = "Starlink 7",
                        launchDateUtc = "2020-06-04T01:25:00.000Z"
                    )
                )
            )
        )

        startFragmentWithEra("upcoming")

        launchListRobot {
            verifyIsRefreshing()

            verifyMissionName("Starlink 7", 0)
            verifyLaunchDate("Jun 4, 2020, 2:25 AM", 0)

            verifySnackbarIsNotVisible()
        }
    }

    @Test
    fun testErrorMessage() {
        returnDataFromRepository(
            expectedLaunchEra = LaunchEra.UPCOMING,
            expectedOrder = LaunchesDateOrder.ASCENDING,
            expectedForceRefresh = false,
            dataToReturn = Resource.error("An Unexpected Error Occurred", null)
        )

        startFragmentWithEra("upcoming")

        launchListRobot {
            verifyIsNotRefreshing()
            verifyRecyclerViewIsEmpty()
            verifySnackbarText("An Unexpected Error Occurred")
        }
    }

    @Test
    fun testErrorMessageWithData() {
        returnDataFromRepository(
            expectedLaunchEra = LaunchEra.UPCOMING,
            expectedOrder = LaunchesDateOrder.ASCENDING,
            expectedForceRefresh = false,
            dataToReturn = Resource.error(
                "An Unexpected Error Occurred",
                listOf(
                    createLaunch(
                        missionName = "Starlink 7",
                        launchDateUtc = "2020-06-04T01:25:00.000Z"
                    )
                )
            )
        )

        startFragmentWithEra("upcoming")

        launchListRobot {
            verifyIsNotRefreshing()

            verifyMissionName("Starlink 7", 0)
            verifyLaunchDate("Jun 4, 2020, 2:25 AM", 0)

            verifySnackbarText("An Unexpected Error Occurred")
        }
    }

    @Test
    fun testSuccess() {
        returnDataFromRepository(
            expectedLaunchEra = LaunchEra.UPCOMING,
            expectedOrder = LaunchesDateOrder.ASCENDING,
            expectedForceRefresh = false,
            dataToReturn = Resource.success(
                listOf(
                    createLaunch(
                        missionName = "Starlink 7",
                        launchDateUtc = "2020-06-04T01:25:00.000Z"
                    )
                )
            )
        )

        startFragmentWithEra("upcoming")

        launchListRobot {
            verifyIsNotRefreshing()

            verifyMissionName("Starlink 7", 0)
            verifyLaunchDate("Jun 4, 2020, 2:25 AM", 0)

            verifySnackbarIsNotVisible()
        }
    }

    private fun startFragmentWithEra(launchEra: String) {
        activityRule.launchActivity(null)
        activityRule.activity.startFragmentInActivity(LaunchListFragment().apply {
            arguments = Bundle().apply { putString("launchEra", launchEra) }
        })
    }

    private fun returnDataFromRepository(
        expectedLaunchEra: LaunchEra,
        expectedOrder: LaunchesDateOrder,
        expectedForceRefresh: Boolean,
        dataToReturn: Resource<List<Launch>>
    ) {
        whenever(
            mockRepository.loadLaunches(
                expectedLaunchEra,
                expectedOrder,
                expectedForceRefresh
            )
        ).thenReturn(
            flowOf(dataToReturn)
        )
    }

    private fun createLaunch(
        id: String = "",
        missionName: String = "",
        launchDateUtc: String = "",
        launchDatePrecision: LaunchDatePrecision = LaunchDatePrecision.HOUR,
        isUpcoming: Boolean = false,
        isLaunchDateTbc: Boolean = false,
        missionPatchUrl: String = "",
        rocketId: String = "",
        launchPadId: String = ""
    ): Launch {
        return Launch(
            id,
            missionName,
            launchDateUtc,
            launchDatePrecision,
            isUpcoming,
            isLaunchDateTbc,
            missionPatchUrl,
            rocketId,
            launchPadId
        )
    }
}