package com.spacex_mvvm.features.launchlist.view

import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import com.spacex_mvvm.features.launchlist.model.LaunchListItem
import com.spacex_mvvm.features.launchlist.usecase.ViewLaunchesUseCase
import kotlinx.coroutines.flow.flowOf

fun launchListViewModelRobot(func: LaunchListViewModelRobot.() -> Unit) =
    LaunchListViewModelRobot().apply { func() }

class LaunchListViewModelRobot {

    private val mockViewLaunchesUseCase = mock<ViewLaunchesUseCase>()

    private val mockLoadingObserver = mock<Observer<Boolean>>()
    private val mockErrorMessageObserver = mock<Observer<String?>>()
    private val mockLaunchesObserver = mock<Observer<List<LaunchListItem>?>>()

    private lateinit var viewModel: LaunchListViewModel

    fun mockLoadLaunchesResource(resource: Resource<List<LaunchListItem>>) {
        whenever(mockViewLaunchesUseCase.loadLaunches(any(), any())).thenReturn(flowOf(resource))
    }

    fun initialiseViewModel() {
        viewModel = LaunchListViewModel(mockViewLaunchesUseCase)
        viewModel.isLoading.observeForever(mockLoadingObserver)
        viewModel.errorMessage.observeForever(mockErrorMessageObserver)
        viewModel.launches.observeForever(mockLaunchesObserver)
    }

    fun loadLaunches() {
        viewModel.loadLaunchesForEra(LaunchEra.UPCOMING, false)
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