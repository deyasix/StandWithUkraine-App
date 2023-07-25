package com.fivesysdev.standwithukraine.mvp

import com.fivesysdev.standwithukraine.data.DayStatistic
import com.fivesysdev.standwithukraine.data.DayStatisticRepository
import com.fivesysdev.standwithukraine.data.DayStatisticRepositoryImpl
import java.time.LocalDate

class StatisticModel : Contract.Model {
    private val dayStatisticRepository: DayStatisticRepository
    private var currentStatistic: DayStatistic? = null

    override var date: LocalDate = LocalDate.now()
        set(value) {
            field = value
            setCurrentStatistic()
        }

    init {
        dayStatisticRepository = DayStatisticRepositoryImpl()
        setCurrentStatistic()
    }

    override fun getNextDayStatistic(): DayStatistic? {
        date = date.plusDays(1)
        setCurrentStatistic()
        return currentStatistic
    }

    override fun getPreviousDayStatistic(): DayStatistic? {
        date = date.minusDays(1)
        setCurrentStatistic()
        return currentStatistic
    }

    override fun getCurrentDayStatistic(): DayStatistic? {
        return currentStatistic
    }

    private fun setCurrentStatistic() {
        val foundStatic = dayStatisticRepository.getStatisticByDate(date.toString())
        if (foundStatic != null) {
            currentStatistic = foundStatic
        }
    }
}
