package com.spacex_mvvm.features.launchlist.usecase

import java.text.DateFormat
import java.text.SimpleDateFormat
import javax.inject.Inject

class UtcToLocalDateConverter @Inject constructor() {

    private val dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)

    fun convertToLocalShortDateString(utcDate: String): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date = formatter.parse(utcDate)
        return dateFormat.format(date)
    }
}