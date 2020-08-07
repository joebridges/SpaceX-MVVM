package com.spacex_mvvm.features.settings.view

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.spacex_mvvm.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ThemeSettingMapper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun convertToAppDelegateTheme(preferenceValue: String): Int {
        return when (preferenceValue) {
            context.getString(R.string.theme_preference_light_value) -> AppCompatDelegate.MODE_NIGHT_NO
            context.getString(R.string.theme_preference_dark_value) -> AppCompatDelegate.MODE_NIGHT_YES
            context.getString(R.string.theme_preference_system_default_value) -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            context.getString(R.string.theme_preference_battery_saver_value) -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            else -> throw IllegalArgumentException("Unrecognized theme preference value $preferenceValue")
        }
    }
}