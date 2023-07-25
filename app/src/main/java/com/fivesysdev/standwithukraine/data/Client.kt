package com.fivesysdev.standwithukraine.data

import android.util.Pair
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.HttpUrl.Builder
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.json.JSONObject
import java.io.IOException
import java.util.Objects

class Client {
    fun getStatisticsFromDate(date: String?): List<DayStatistic> {
        var fetchedStatistics: List<DayStatistic> = ArrayList()
        val endpoint = "/statistics"
        val httpBuilder: Builder = (URL + endpoint).toHttpUrl().newBuilder()
        httpBuilder.addQueryParameter("date_from", date)
        val request: Request = Request.Builder()
            .url(httpBuilder.build())
            .build()
        httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            response.body.use { responseBody ->
                if (responseBody != null) fetchedStatistics = putStatisticsToList(responseBody)
            }
        }
        return fetchedStatistics
    }

    private fun putStatisticsToList(body: ResponseBody): List<DayStatistic> {
        val fetchedStatistics = ArrayList<DayStatistic>()
        val days = JSONObject(body.string())
            .getJSONObject("data")
            .getJSONArray("records")
        for (i in 0 until days.length()) {
            val dayDate = JSONObject(days[i].toString()).getString("date")
            val dayStats = days.getJSONObject(i).getJSONObject("stats")
            val increaseStats = days.getJSONObject(i).getJSONObject("increase")
            val names = dayStats.names()
            val dayStatistics = ArrayList<Pair<Int, Int>>()
            for (j in 0 until Objects.requireNonNull(names).length()) {
                val quantity = dayStats.getInt(names?.getString(j) ?: "0")
                val increaseQuantity = increaseStats.getInt(names?.getString(j) ?: "0")
                dayStatistics.add(Pair(quantity, increaseQuantity))
            }
            fetchedStatistics.add(DayStatistic(dayDate, dayStatistics))
        }
        return fetchedStatistics
    }

    companion object {
        private val httpClient = OkHttpClient()
        private const val URL = "https://russianwarship.rip/api/v2"
    }
}