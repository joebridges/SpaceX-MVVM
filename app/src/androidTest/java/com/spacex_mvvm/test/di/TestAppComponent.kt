package com.spacex_mvvm.test.di

import android.content.Context
import com.spacex_mvvm.di.AppComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [TestViewModelModule::class]
)
interface TestAppComponent: AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: Context): TestAppComponent
    }
}