package com.spacex_mvvm.features.launchlist.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import com.spacex_mvvm.data.Status
import com.spacex_mvvm.features.launchlist.usecase.ViewPastLaunchesUseCase
import javax.inject.Inject

class LaunchListViewModel @Inject constructor(
    viewPastLaunchesUseCase: ViewPastLaunchesUseCase
) : ViewModel() {

    private val resource = viewPastLaunchesUseCase.loadPastLaunches().asLiveData()

    val isLoading = resource.map { resource ->
        resource.status == Status.LOADING
    }.distinctUntilChanged()

    val launches = resource.map { resource ->
        resource.data
    }.distinctUntilChanged()

    val errorMessage = resource.map { resource ->
        resource.errorMessage
    }.distinctUntilChanged()
}