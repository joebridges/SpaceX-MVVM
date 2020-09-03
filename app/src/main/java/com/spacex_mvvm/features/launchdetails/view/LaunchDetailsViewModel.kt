package com.spacex_mvvm.features.launchdetails.view

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.spacex_mvvm.features.launchdetails.usecase.ViewLaunchDetailsUseCase

class LaunchDetailsViewModel @ViewModelInject constructor(
    private val viewLaunchDetailsUseCase: ViewLaunchDetailsUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val launchId = savedStateHandle.getLiveData<String>(LAUNCH_ID_SAVED_STATE_KEY)

    private val launchDetailsResource = launchId.switchMap { id ->
        viewLaunchDetailsUseCase.loadLaunch(id).asLiveData()
    }

    val launchDetails = launchDetailsResource.map { resource -> resource.data }

    fun loadLaunch(launchId: String) {
        if (this.launchId.value != launchId) {
            this.launchId.value = launchId
        }
    }

    companion object {
        const val LAUNCH_ID_SAVED_STATE_KEY = "LAUNCH_ID"
    }
}