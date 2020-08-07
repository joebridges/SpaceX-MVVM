package com.spacex_mvvm.data.network

import com.spacex_mvvm.data.network.model.request.LaunchesRequestOptions
import com.spacex_mvvm.data.network.model.response.LaunchesResponseEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface SpaceXService {

    @POST("launches/query")
    suspend fun getLaunches(@Body requestOptions: LaunchesRequestOptions): LaunchesResponseEntity

    companion object {
        const val UPCOMING_LAUNCH_PATH = "upcoming"
        const val PAST_LAUNCHES_PATH = "past"

        const val ORDER_ASC = "asc"
        const val ORDER_DESC = "desc"
    }
}