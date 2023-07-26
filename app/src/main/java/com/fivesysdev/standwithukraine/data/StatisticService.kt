package com.fivesysdev.standwithukraine.data

import com.fivesysdev.standwithukraine.data.model.Data

class StatisticService {
    private val retrofit = RetrofitClient.getClient()
    private val statisticAPI = retrofit.create(APIService::class.java)

    fun getStatisticFromDate(fromDate: String): List<DayStatistic> {
        val response = statisticAPI.getStatisticFromDate(fromDate).execute()
        val statistics = mutableListOf<DayStatistic>()
        if (response.isSuccessful) {
            val body: Data? = response.body()?.data
            val records = body?.records
            records?.forEach {
                statistics.add(DayStatistic(it.date, it.stats, it.increase))
            }
        }
        return statistics
    }
}