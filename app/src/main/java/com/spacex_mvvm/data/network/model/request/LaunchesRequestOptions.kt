package com.spacex_mvvm.data.network.model.request

import com.squareup.moshi.Json

data class LaunchesRequestOptions private constructor(
    @field:Json(name = "query") val query: Query,
    @field:Json(name = "options") val options: Options
) {
    companion object {
        fun create(upcoming: Boolean, orderBy: String): LaunchesRequestOptions {
            return LaunchesRequestOptions(
                Query(upcoming),
                Options(Sort(orderBy), pagination = false)
            )
        }
    }
}

data class Query(@field:Json(name = "upcoming") val upcoming: Boolean)

data class Options(@field:Json(name = "sort") val sort: Sort, val pagination: Boolean)

data class Sort(@field:Json(name = "flight_number") val sortBy: String)