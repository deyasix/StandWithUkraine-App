package com.fivesysdev.standwithukraine.data

interface DayStatisticRepository {
    suspend fun getStatisticByDate(date: String?): DayStatistic?
}
