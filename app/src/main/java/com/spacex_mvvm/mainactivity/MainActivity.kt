package com.spacex_mvvm.mainactivity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.spacex_mvvm.R
import com.spacex_mvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val topLevelFragmentIds = setOf(R.id.pastLaunches, R.id.upcomingLaunches)

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigationController = findNavController(R.id.navHost)
        setUpActionBar(navigationController)
        setUpBottomNavigation(navigationController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_bar_items, menu)
        setUpActionBarMenuItems(menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.actionSettings) {
            findNavController(R.id.navHost).navigate(R.id.settingsFragment)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.navHost).navigateUp()

    private fun setUpActionBar(navController: NavController) {
        setSupportActionBar(binding.appBar)
        val topLevelFragmentsConfiguration = AppBarConfiguration.Builder(
            topLevelFragmentIds
        ).build()
        setupActionBarWithNavController(navController, topLevelFragmentsConfiguration)
        navController.addOnDestinationChangedListener { _, _, _ -> invalidateOptionsMenu() }
    }

    private fun setUpBottomNavigation(navController: NavController) {
        binding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.isVisible = topLevelFragmentIds.contains(destination.id)
        }
    }

    private fun setUpActionBarMenuItems(menu: Menu) {
        val settingsMenuItem = menu.findItem(R.id.actionSettings)
        settingsMenuItem.isVisible = isTopLevelFragment()
    }

    private fun isTopLevelFragment() =
        topLevelFragmentIds.contains(findNavController(R.id.navHost).currentDestination?.id)
}
