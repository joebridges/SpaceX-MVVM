package com.spacex_mvvm.data.database.di

import android.content.Context
import androidx.room.Room
import com.spacex_mvvm.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "App Database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideLaunchesDao(appDatabase: AppDatabase) = appDatabase.getLaunchesDao()

    @Singleton
    @Provides
    fun provideRocketsDao(appDatabase: AppDatabase) = appDatabase.getRocketsDao()

    @Singleton
    @Provides
    fun provideSitesDao(appDatabase: AppDatabase) = appDatabase.getSitesDao()
}