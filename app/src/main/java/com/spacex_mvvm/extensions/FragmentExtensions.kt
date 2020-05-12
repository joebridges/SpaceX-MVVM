package com.spacex_mvvm.extensions

import androidx.fragment.app.Fragment
import com.spacex_mvvm.SpaceXApplication

fun Fragment.requireSpaceXApplication(): SpaceXApplication {
    return (requireActivity().application) as SpaceXApplication
}