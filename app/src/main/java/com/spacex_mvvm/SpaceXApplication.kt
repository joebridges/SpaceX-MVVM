package com.spacex_mvvm

import android.app.Application
import com.spacex_mvvm.data.repositories.settings.SettingsRepository
import com.spacex_mvvm.ui.ThemeManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SpaceXApplication : Application() {

    @Inject
    lateinit var themeManager: ThemeManager

    override fun onCreate() {
        super.onCreate()
        themeManager.setUserSpecifiedTheme()
    }
}