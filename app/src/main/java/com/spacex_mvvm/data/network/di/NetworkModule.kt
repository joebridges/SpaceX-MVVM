package com.spacex_mvvm.data.network.di

import com.spacex_mvvm.data.network.SpaceXService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideSpaceXService(): SpaceXService {
        return Retrofit.Builder()
            .baseUrl("https://api.spacexdata.com/v3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SpaceXService::class.java)
    }
}