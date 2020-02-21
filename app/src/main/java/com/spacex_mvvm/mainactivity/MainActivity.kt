package com.spacex_mvvm.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spacex_mvvm.R
import com.spacex_mvvm.features.launchlist.view.LaunchListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,
                    LaunchListFragment()
                )
                .commit()
        }
    }
}
