package com.spacex_mvvm.features.launchlist.model

import android.content.Context
import com.spacex_mvvm.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class LaunchDateFormatter @Inject constructor(
    private val context: Context
) {
    private val inputDateFormat = SimpleDateFormat(INPUT_DATE_FORMAT).apply {
        timeZone = TimeZone.getTimeZone(UTC_TIME_ZONE_ID)
    }

    private val shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
    private val tentativeDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
    private val tbdDateFormat = SimpleDateFormat(TBD_DATE_FORMAT, Locale.getDefault())

    fun formatLaunchDate(utcDate: String, isDateTbd: Boolean, isDateTentative: Boolean): String {
        return when {
            isDateTbd -> formatAsTbdDate(utcDate)
            isDateTentative -> formatAsTentativeDate(utcDate)
            else -> formatAsLocalShortDateString(utcDate)
        }
    }

    private fun formatAsTbdDate(utcDate: String): String {
        val date = inputDateFormat.parse(utcDate)
        return "${tbdDateFormat.format(date)} ${context.getString(R.string.date_tbd)}"
    }

    private fun formatAsTentativeDate(utcDate: String): String {
        val date = inputDateFormat.parse(utcDate)
        return "${tentativeDateFormat.format(date)} ${context.getString(R.string.time_tbd)}"
    }

    private fun formatAsLocalShortDateString(utcDate: String): String {
        val date = inputDateFormat.parse(utcDate)
        return shortDateFormat.format(date)
    }

    companion object {
        const val INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val TBD_DATE_FORMAT = "MMM yyyy"

        const val UTC_TIME_ZONE_ID = "UTC"
    }
}