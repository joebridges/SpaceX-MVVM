package com.spacex_mvvm.di

import android.content.Context
import com.spacex_mvvm.data.repositories.di.RepositoriesModule
import com.spacex_mvvm.features.launchlist.di.LaunchListModule
import com.spacex_mvvm.features.launchlist.view.LaunchListFragment
import com.spacex_mvvm.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        RepositoriesModule::class,
        LaunchListModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }

    fun inject(launchListFragment: LaunchListFragment)
}