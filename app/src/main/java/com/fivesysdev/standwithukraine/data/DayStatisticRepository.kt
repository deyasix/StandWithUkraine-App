package com.fivesysdev.standwithukraine.data

interface DayStatisticRepository {
    fun getStatisticByDate(date: String?): DayStatistic?
}
