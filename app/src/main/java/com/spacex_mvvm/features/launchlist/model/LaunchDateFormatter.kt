package com.spacex_mvvm.features.launchlist.model

import com.spacex_mvvm.data.repositories.launches.model.LaunchDatePrecision
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

class LaunchDateFormatter @Inject constructor(
    private val yearHalfDateFormatter: YearHalfDateFormatter
) {

    private val shortDateTimeFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
    private val shortDateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    private val monthFormat = DateTimeFormatter.ofPattern(MONTH_FORMAT)
    private val quarterFormat = DateTimeFormatter.ofPattern(QUARTER_FORMAT)
    private val yearFormat = DateTimeFormatter.ofPattern(YEAR_FORMAT)

    fun formatLaunchDate(
        utcDate: String,
        datePrecision: LaunchDatePrecision
    ): String {
        return if (shouldConvertToLocalDateTime(datePrecision)) {
            val localLaunchDateTime = Instant.parse(utcDate).atZone(ZoneId.systemDefault())
            shortDateTimeFormat.format(localLaunchDateTime)
        } else {
            val utcLaunchDateTime = Instant.parse(utcDate).atZone(ZoneId.of(UTC_TIME_ZONE))
            when (datePrecision) {
                LaunchDatePrecision.YEAR -> yearFormat.format(utcLaunchDateTime)
                LaunchDatePrecision.HALF -> yearHalfDateFormatter.format(utcLaunchDateTime)
                LaunchDatePrecision.QUARTER -> quarterFormat.format(utcLaunchDateTime)
                LaunchDatePrecision.MONTH -> monthFormat.format(utcLaunchDateTime)
                else -> shortDateFormat.format(utcLaunchDateTime)
            }
        }
    }

    // Only convert to local time if date precision is set as an hour because lesser precisions such as
    // month are defined as midnight on the first day of the month so conversion to a local date time
    // can cause the date and therefore the month to change and become incorrect
    private fun shouldConvertToLocalDateTime(datePrecision: LaunchDatePrecision) =
        datePrecision == LaunchDatePrecision.HOUR || datePrecision == LaunchDatePrecision.UNKNOWN

    companion object {
        const val MONTH_FORMAT = "MMM yyyy"
        const val QUARTER_FORMAT = "'Q'q yyyy"
        const val YEAR_FORMAT = "yyyy"

        const val UTC_TIME_ZONE = "UTC"
    }
}