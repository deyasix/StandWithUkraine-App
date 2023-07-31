package com.fivesysdev.standwithukraine.data

import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class DayStatisticRepositoryImpl : DayStatisticRepository {
    private var statistics: MutableList<DayStatistic> = ArrayList()
    private var fromDate: LocalDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth())
    private val statisticService = StatisticService()

    private suspend fun updateStatistics() {
        addAll(statisticService.getStatisticFromDate(fromDate.toString()))
    }

    override suspend fun getStatisticByDate(date: String?): DayStatistic? {
        updateStatistics()
        val selectedDate = LocalDate.parse(date)
        if (selectedDate.isBefore(fromDate)) {
            fromDate =
                fromDate.minusMonths((fromDate.month - selectedDate.month.value.toLong()).value.toLong())
            updateStatistics()
        }
        var statistic = findStatistic(date)
        if (statistic == null) {
            fromDate = selectedDate
            updateStatistics()
            statistic = findStatistic(date)
        }
        return statistic
    }

    private fun findStatistic(date: String?): DayStatistic? {
        for (statistic in statistics) {
            if (statistic.date == date) {
                return statistic
            }
        }
        return null
    }

    private fun addAll(list: List<DayStatistic>) {
        for (item in list) {
            if (statistics.none { it == item }) {
                statistics.add(item)
            }
        }
    }
}
