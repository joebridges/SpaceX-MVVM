package com.spacex_mvvm.ui

import androidx.appcompat.app.AppCompatDelegate
import com.spacex_mvvm.data.repositories.settings.SettingsRepository
import javax.inject.Inject

class ThemeManager @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    fun setUserSpecifiedTheme() {
        val theme = settingsRepository.getTheme()
        AppCompatDelegate.setDefaultNightMode(theme)
    }
}