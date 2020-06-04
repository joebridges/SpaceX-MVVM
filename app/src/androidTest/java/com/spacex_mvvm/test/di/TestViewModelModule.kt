package com.spacex_mvvm.test.di

import androidx.lifecycle.ViewModelProvider
import com.nhaarman.mockito_kotlin.mock
import com.spacex_mvvm.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object TestViewModelModule {

    val viewModelFactory: ViewModelFactory = mock()

    @JvmStatic
    @Provides
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return viewModelFactory
    }
}