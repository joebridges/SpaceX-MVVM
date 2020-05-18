package com.spacex_mvvm.test

import com.spacex_mvvm.SpaceXApplication
import com.spacex_mvvm.di.AppComponent
import com.spacex_mvvm.test.di.DaggerTestAppComponent

class TestApplication: SpaceXApplication() {

    override fun initializeAppComponent(): AppComponent {
        return DaggerTestAppComponent.factory().create(applicationContext)
    }
}