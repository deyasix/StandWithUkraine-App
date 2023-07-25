package com.fivesysdev.standwithukraine.data

import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.util.concurrent.Callable
import java.util.concurrent.FutureTask

class DayStatisticRepositoryImpl : DayStatisticRepository {
    private var statistics: MutableList<DayStatistic> = ArrayList()
    var fromDate: LocalDate

    init {
        fromDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth())
        updateStatistics()
    }

    private fun updateStatistics() {
        val task = Callable {
            val client = Client()
            return@Callable client.getStatisticsFromDate(fromDate.toString())
        }
        val future = FutureTask(task)
        Thread(future).start()
        addAll(future.get())
    }

    override fun getStatisticByDate(date: String?): DayStatistic? {
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
            if (statistics.stream().noneMatch { element: DayStatistic -> element == item }) {
                statistics.add(item)
            }
        }
    }
}
