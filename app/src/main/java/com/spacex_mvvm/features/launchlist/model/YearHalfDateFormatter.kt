package com.spacex_mvvm.features.launchlist.model

import android.content.Context
import com.spacex_mvvm.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.ZonedDateTime
import javax.inject.Inject

class YearHalfDateFormatter @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun format(dateTime: ZonedDateTime): String {
        val halfPrefix = context.getString(R.string.year_half_prefix)
        val halfNumber = if (dateTime.monthValue <= 6) 1 else 2
        return "$halfPrefix$halfNumber ${dateTime.year}"
    }
}