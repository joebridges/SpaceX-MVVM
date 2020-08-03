package com.spacex_mvvm.data.repositories.launches.model

import org.junit.Assert.assertEquals
import org.junit.Test

class LaunchEraTest {

    @Test
    fun testUpcomingUrlPathParam() {
        val result = LaunchEra.UPCOMING.toUrlPathParam()
        assertEquals("upcoming", result)
    }

    @Test
    fun testPastUrlPathParam() {
        val result = LaunchEra.PAST.toUrlPathParam()
        assertEquals("past", result)
    }

    @Test
    fun testUpcomingDefaultOrdering() {
        val result = LaunchEra.UPCOMING.getDefaultOrderQueryParam()
        assertEquals("asc", result)
    }

    @Test
    fun testPastDefaultOrdering() {
        val result = LaunchEra.PAST.getDefaultOrderQueryParam()
        assertEquals("desc", result)
    }
}