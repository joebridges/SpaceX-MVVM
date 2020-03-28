package com.spacex_mvvm.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spacex_mvvm.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationController = findNavController(R.id.nav_host_fragment)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.mainActivityBottomNav)
        bottomNavigation.setupWithNavController(navigationController)
    }
}
