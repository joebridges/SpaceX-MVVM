package com.spacex_mvvm.data.network.model.request

import com.squareup.moshi.Json

class LaunchesRequestOptions private constructor(
    @field:Json(name = "query") val query: Query,
    @field:Json(name = "options") val options: Options
) {
    companion object {
        fun from(upcoming: Boolean, orderBy: String): LaunchesRequestOptions {
            return LaunchesRequestOptions(Query(upcoming), Options(Sort(orderBy)))
        }
    }
}

class Query(@field:Json(name = "upcoming") val upcoming: Boolean)

class Options(@field:Json(name = "sort") val sort: Sort)

class Sort(@field:Json(name = "date_unix") val sortBy: String)