package com.spacex_mvvm.data.repositories.settings

import android.content.Context
import android.content.SharedPreferences
import com.spacex_mvvm.R
import com.spacex_mvvm.features.settings.view.ThemeSettingMapper
import javax.inject.Inject

class SettingsSharedPreferencesRepository @Inject constructor(
    context: Context,
    private val sharedPreferences: SharedPreferences,
    private val themeSettingMapper: ThemeSettingMapper
) : SettingsRepository {

    private val defaultTheme = context.getString(R.string.theme_preference_default_value)

    override fun getTheme(): Int {
        val sharedPrefsValue = sharedPreferences.getString(
            SettingsConstants.THEME_KEY,
            defaultTheme
        ) ?: defaultTheme
        return themeSettingMapper.convertToAppDelegateTheme(sharedPrefsValue)
    }
}