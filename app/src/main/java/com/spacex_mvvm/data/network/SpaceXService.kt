package com.spacex_mvvm.data.network

import com.spacex_mvvm.data.network.model.Launch
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpaceXService {

    @GET("launches/{launchEra}")
    suspend fun getLaunches(
        @Path("launchEra") launchEra: String,
        @Query("order") order: String
    ): List<Launch>


    companion object {
        const val UPCOMING_LAUNCH_PATH = "upcoming"
        const val PAST_LAUNCHES_PATH = "past"

        const val ORDER_ASC = "asc"
        const val ORDER_DESC = "desc"
    }
}