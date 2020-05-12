package com.spacex_mvvm.features.launchlist.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.spacex_mvvm.data.Status
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import com.spacex_mvvm.features.launchlist.usecase.ViewLaunchesUseCase
import javax.inject.Inject

class LaunchListViewModel @Inject constructor(
    private val viewLaunchesUseCase: ViewLaunchesUseCase
) : ViewModel() {

    private val loadLaunchesArgs = MutableLiveData<LoadLaunchesArgs>()

    private val resource = loadLaunchesArgs.switchMap { args ->
        viewLaunchesUseCase.loadLaunches(args.launchEra, args.forceRefresh).asLiveData()
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
        this.loadLaunchesArgs.value = LoadLaunchesArgs(launchEra, forceRefresh)
    }
}

private data class LoadLaunchesArgs(
    val launchEra: LaunchEra,
    val forceRefresh: Boolean
)