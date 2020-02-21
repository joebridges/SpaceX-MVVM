package com.spacex_mvvm.di

import android.app.Application
import com.spacex_mvvm.features.launchlist.view.LaunchListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(launchListFragment: LaunchListFragment)
}