package com.spacex_mvvm

import android.app.Application
import com.spacex_mvvm.di.DaggerAppComponent

class SpaceXApplication : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(this.applicationContext)
    }
}