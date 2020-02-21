package com.spacex_mvvm.features.launchlist.di

import androidx.lifecycle.ViewModel
import com.spacex_mvvm.features.launchlist.view.LaunchListViewModel
import com.spacex_mvvm.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LaunchListModule {

    @Binds
    @IntoMap
    @ViewModelKey(LaunchListViewModel::class)
    abstract fun bindLaunchListViewModel(viewModel: LaunchListViewModel): ViewModel
}