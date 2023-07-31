package com.fivesysdev.standwithukraine.data

import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class DayStatisticRepositoryImpl : DayStatisticRepository {
    private var statistics: MutableList<DayStatistic> = ArrayList()
    private var fromDate: LocalDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth())

    private suspend fun updateStatistics() {
        addAll(StatisticService().getStatisticFromDate(fromDate.toString()))
    }

    override suspend fun getStatisticByDate(date: String?): DayStatistic? {
        updateStatistics()
        if (LocalDate.parse(date).isBefore(fromDate)) {
            fromDate = fromDate.minusMonths(1)
            updateStatistics()
        }
        for (statistic in statistics) {
            if (statistic.date == date) {
                return statistic
            }
        }
        return null
    }

    private fun addAll(list: List<DayStatistic>) {
        for (item in list) {
            if (statistics.none { it == item }) { statistics.add(item) }
        }
    }
}
