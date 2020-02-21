package com.spacex_mvvm.features.launchlist.view

import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.features.launchlist.usecase.LaunchListItem
import com.spacex_mvvm.features.launchlist.usecase.ViewPastLaunchesUseCase
import kotlinx.coroutines.flow.flowOf

fun launchListViewModelRobot(func: LaunchListViewModelRobot.() -> Unit) =
    LaunchListViewModelRobot().apply { func() }

class LaunchListViewModelRobot {

    private val mockPastLaunchesUseCase = mock<ViewPastLaunchesUseCase>()

    private val mockLoadingObserver = mock<Observer<Boolean>>()
    private val mockErrorMessageObserver = mock<Observer<String?>>()
    private val mockLaunchesObserver = mock<Observer<List<LaunchListItem>?>>()

    private lateinit var viewModel: LaunchListViewModel

    fun mockLoadPastLaunchesResource(resource: Resource<List<LaunchListItem>>) {
        whenever(mockPastLaunchesUseCase.loadPastLaunches()).thenReturn(flowOf(resource))
    }

    fun initialiseViewModel() {
        viewModel = LaunchListViewModel(mockPastLaunchesUseCase)
        viewModel.isLoading.observeForever(mockLoadingObserver)
        viewModel.errorMessage.observeForever(mockErrorMessageObserver)
        viewModel.launches.observeForever(mockLaunchesObserver)
    }

    fun verifyIsLoading(isLoading: Boolean) {
        verify(mockLoadingObserver).onChanged(isLoading)
    }

    fun verifyErrorMessage(errorMessage: String?) {
        verify(mockErrorMessageObserver).onChanged(errorMessage)
    }

    fun verifyLaunches(launches: List<LaunchListItem>?) {
        verify(mockLaunchesObserver).onChanged(launches)
    }
}