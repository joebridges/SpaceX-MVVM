package com.spacex_mvvm.di

import android.app.Application
import com.spacex_mvvm.data.database.di.DatabaseModule
import com.spacex_mvvm.data.network.di.NetworkModule
import com.spacex_mvvm.features.launchlist.di.LaunchListModule
import com.spacex_mvvm.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    DatabaseModule::class,
    LaunchListModule::class,
    NetworkModule::class,
    ViewModelModule::class
])
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: Application) = app.applicationContext
}