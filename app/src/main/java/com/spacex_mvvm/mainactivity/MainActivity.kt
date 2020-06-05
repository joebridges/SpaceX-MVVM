package com.spacex_mvvm.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spacex_mvvm.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationController = findNavController(R.id.nav_host_fragment)
        setUpActionBar(navigationController)
        setUpBottomNavigation(navigationController)
    }

    private fun setUpActionBar(navController: NavController) {
        val topLevelFragmentsConfiguration = AppBarConfiguration.Builder(
            R.id.pastLaunches,
            R.id.upcomingLaunches
        ).build()

        setupActionBarWithNavController(navController, topLevelFragmentsConfiguration)
    }

    private fun setUpBottomNavigation(navController: NavController) {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.mainActivityBottomNav)
        bottomNavigation.setupWithNavController(navController)
    }
}
