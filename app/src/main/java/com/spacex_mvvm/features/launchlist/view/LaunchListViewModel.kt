package com.spacex_mvvm.features.launchlist.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.spacex_mvvm.data.Status
import com.spacex_mvvm.features.launchlist.usecase.ViewLaunchesUseCase
import javax.inject.Inject

class LaunchListViewModel @Inject constructor(
    viewLaunchesUseCase: ViewLaunchesUseCase
) : ViewModel() {

    private val launchEra = MutableLiveData<Pair<LaunchEra, Boolean>>()

    private val resource = launchEra.switchMap { launchEra ->
        viewLaunchesUseCase.loadLaunches(launchEra.first, launchEra.second).asLiveData()
    }

    val isLoading = resource.map { resource ->
        resource.status == Status.LOADING
    }.distinctUntilChanged()

    val launches = resource.map { resource ->
        resource.data
    }.distinctUntilChanged()

    val errorMessage = resource.map { resource ->
        resource.errorMessage
    }.distinctUntilChanged()

    fun loadLaunchesForEra(launchEra: LaunchEra, forceRefresh: Boolean = false) {
        this.launchEra.value = launchEra to forceRefresh
    }
}