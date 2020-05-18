package com.spacex_mvvm.test

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class SpaceXTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = super.newApplication(cl, TestApplication::class.java.name, context)
}