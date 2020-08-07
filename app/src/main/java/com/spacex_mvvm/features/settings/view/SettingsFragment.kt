package com.spacex_mvvm.features.settings.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.spacex_mvvm.R
import com.spacex_mvvm.data.repositories.settings.SettingsConstants
import com.spacex_mvvm.di.AppModule
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var themeSettingMapper: ThemeSettingMapper

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = AppModule.SHARED_PREFS_KEY
        preferenceManager.sharedPreferencesMode = Context.MODE_PRIVATE
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        findPreference<ListPreference>(SettingsConstants.THEME_KEY)?.setOnPreferenceChangeListener { _, newValue ->
            val theme = newValue as String
            AppCompatDelegate.setDefaultNightMode(themeSettingMapper.convertToAppDelegateTheme(theme))
            return@setOnPreferenceChangeListener true
        }
    }
}