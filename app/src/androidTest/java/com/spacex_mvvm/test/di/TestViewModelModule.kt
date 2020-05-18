package com.spacex_mvvm.test.di

import androidx.lifecycle.ViewModelProvider
import com.nhaarman.mockito_kotlin.mock
import com.spacex_mvvm.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
object TestViewModelModule {

    val viewModelFactory: ViewModelFactory = mock()

    @JvmStatic
    @Provides
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return viewModelFactory
    }
}