package com.spacex_mvvm

import android.app.Application
import com.spacex_mvvm.di.DaggerAppComponent

open class SpaceXApplication : Application() {

    val appComponent by lazy {
        initializeAppComponent()
    }

    open fun initializeAppComponent() =
        DaggerAppComponent.factory().create(this.applicationContext)
}