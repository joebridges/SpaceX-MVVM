package com.spacex_mvvm.data.repositories.di

import com.spacex_mvvm.data.database.launches.LaunchesDao
import com.spacex_mvvm.data.mappers.launch.LaunchEntityMapper
import com.spacex_mvvm.data.mappers.launch.LaunchesResponseMapper
import com.spacex_mvvm.data.network.SpaceXService
import com.spacex_mvvm.data.repositories.RateLimiter
import com.spacex_mvvm.data.repositories.launches.LaunchesDataRepository
import com.spacex_mvvm.data.repositories.launches.LaunchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideLaunchesRepository(
        spaceXService: SpaceXService,
        launchesDao: LaunchesDao,
        launchEntityMapper: LaunchEntityMapper,
        launchesResponseMapper: LaunchesResponseMapper
    ): LaunchesRepository {
        return LaunchesDataRepository(
            spaceXService,
            launchesDao,
            launchEntityMapper,
            launchesResponseMapper,
            RateLimiter(10, TimeUnit.MINUTES)
        )
    }
}