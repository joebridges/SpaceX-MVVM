package com.spacex_mvvm.test

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HiltEmptyFragmentActivity : FragmentScenario.EmptyFragmentActivity() {

    fun startFragmentInActivity(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, fragment)
            .commit()
    }
}