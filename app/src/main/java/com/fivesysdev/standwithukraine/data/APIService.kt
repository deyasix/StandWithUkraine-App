package com.fivesysdev.standwithukraine.data

import com.fivesysdev.standwithukraine.data.model.Body
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("statistics")
    suspend fun getStatisticFromDate(@Query("date_from") dateFrom: String): Response<Body>
}