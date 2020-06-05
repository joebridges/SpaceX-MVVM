package com.spacex_mvvm.test.features.launchlist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.spacex_mvvm.R
import com.spacex_mvvm.test.custommatchers.RecyclerViewCustomMatchers.atPosition
import com.spacex_mvvm.test.custommatchers.RecyclerViewCustomMatchers.hasItemCount
import com.spacex_mvvm.test.custommatchers.SwipeRefreshLayoutCustomMatchers.isRefreshing
import org.hamcrest.CoreMatchers.not

fun launchListRobot(func: LaunchListRobot.() -> Unit) = LaunchListRobot().apply {
    func()
}

class LaunchListRobot {

    fun swipeToRefresh() {
        onView(withId(R.id.swipeRefresh)).perform(swipeDown())
    }

    fun verifyIsRefreshing() {
        onView(withId(R.id.swipeRefresh)).check(matches(isRefreshing()))
    }

    fun verifyIsNotRefreshing() {
        onView(withId(R.id.swipeRefresh)).check(matches(not(isRefreshing())))
    }

    fun verifyRecyclerViewIsEmpty() {
        onView(withId(R.id.recyclerView)).check(matches(hasItemCount(0)))
    }

    fun verifySnackbarText(expectedText: String) {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(expectedText)))
    }

    fun verifySnackbarIsNotVisible() {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(doesNotExist())
    }

    fun verifyMissionName(expectedMissionName: String, position: Int) {
        onView(withId(R.id.recyclerView)).check(
            matches(
                atPosition(
                    position,
                    R.id.missionName,
                    withText(expectedMissionName)
                )
            )
        )
    }

    fun verifyLaunchDate(expectedDate: String, position: Int) {
        onView(withId(R.id.recyclerView)).check(
            matches(
                atPosition(
                    position,
                    R.id.launchDate,
                    withText(expectedDate)
                )
            )
        )
    }

    fun verifySiteName(expectedSiteName: String, position: Int) {
        onView(withId(R.id.recyclerView)).check(
            matches(
                atPosition(
                    position,
                    R.id.siteName,
                    withText(expectedSiteName)
                )
            )
        )
    }

    fun verifyRocketName(expectedRocketName: String, position: Int) {
        onView(withId(R.id.recyclerView)).check(
            matches(
                atPosition(
                    position,
                    R.id.rocketName,
                    withText(expectedRocketName)
                )
            )
        )
    }
}