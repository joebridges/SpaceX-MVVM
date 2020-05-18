package com.spacex_mvvm.test.features.launchlist

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.spacex_mvvm.R
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import com.spacex_mvvm.features.launchlist.model.LaunchListItem
import com.spacex_mvvm.features.launchlist.view.LaunchListFragment
import com.spacex_mvvm.features.launchlist.view.LaunchListViewModel
import com.spacex_mvvm.test.custommatchers.RecyclerViewCustomMatchers.atPosition
import com.spacex_mvvm.test.custommatchers.RecyclerViewCustomMatchers.hasItemCount
import com.spacex_mvvm.test.custommatchers.SwipeRefreshLayoutCustomMatchers.isRefreshing
import com.spacex_mvvm.test.di.TestViewModelModule
import org.hamcrest.CoreMatchers.not

fun launchListRobot(func: LaunchListRobot.() -> Unit) = LaunchListRobot().apply {
    func()
}

class LaunchListRobot {

    lateinit var mockViewModel: LaunchListViewModel

    private lateinit var isLoading: MutableLiveData<Boolean>
    private lateinit var errorMessage: MutableLiveData<String?>
    private lateinit var data: MutableLiveData<List<LaunchListItem>?>

    fun initialiseMockViewModel() {
        mockViewModel = mock()

        whenever(TestViewModelModule.viewModelFactory.create(LaunchListViewModel::class.java))
            .thenReturn(mockViewModel)

        isLoading = MutableLiveData()
        errorMessage = MutableLiveData()
        data = MutableLiveData()

        whenever(mockViewModel.isLoading).thenReturn(isLoading)
        whenever(mockViewModel.errorMessage).thenReturn(errorMessage)
        whenever(mockViewModel.launches).thenReturn(data)
    }

    fun launchFragment(launchEra: String) {
        launchFragmentInContainer<LaunchListFragment>(
            Bundle().apply { putString("launchEra", launchEra) },
            R.style.AppTheme
        )
    }

    fun verifyLoadLaunchesIsCalled(launchEra: LaunchEra, forceRefresh: Boolean) {
        verify(mockViewModel).loadLaunchesForEra(launchEra, forceRefresh)
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.postValue(isLoading)
    }

    fun setErrorMessage(message: String?) {
        this.errorMessage.postValue(message)
    }

    fun setData(launches: List<LaunchListItem>?) {
        this.data.postValue(launches)
    }

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

    fun verifyLaunchMissionName(expectedMissionName: String, position: Int) {
        onView(withId(R.id.recyclerView)).check(matches(atPosition(
            position,
            R.id.missionName,
            withText(expectedMissionName)
        )))
    }

    fun verifyLaunchDate(expectedDate: String, position: Int) {
        onView(withId(R.id.recyclerView)).check(matches(atPosition(
            position,
            R.id.launchDate,
            withText(expectedDate)
        )))
    }

    fun verifySiteName(expectedSiteName: String, position: Int) {
        onView(withId(R.id.recyclerView)).check(matches(atPosition(
            position,
            R.id.siteName,
            withText(expectedSiteName)
        )))
    }

    fun verifyRocketName(expectedRocketName: String, position: Int) {
        onView(withId(R.id.recyclerView)).check(matches(atPosition(
            position,
            R.id.rocketName,
            withText(expectedRocketName)
        )))
    }
}