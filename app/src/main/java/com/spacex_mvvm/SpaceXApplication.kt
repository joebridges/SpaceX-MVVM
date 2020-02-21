package com.spacex_mvvm

import android.app.Application
import com.spacex_mvvm.di.AppComponent
import com.spacex_mvvm.di.DaggerAppComponent

class SpaceXApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    companion object {
        private var INSTANCE: SpaceXApplication? = null
        @JvmStatic
        fun get(): SpaceXApplication = INSTANCE!!
    }
}