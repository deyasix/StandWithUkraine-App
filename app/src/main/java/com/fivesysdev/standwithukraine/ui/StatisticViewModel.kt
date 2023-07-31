package com.fivesysdev.standwithukraine.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fivesysdev.standwithukraine.data.DayStatistic
import com.fivesysdev.standwithukraine.data.DayStatisticRepositoryImpl
import java.time.LocalDate

class StatisticViewModel : ViewModel() {

    private val dayStatistic = MutableLiveData<DayStatistic>()
    private val dayStatisticRepository = DayStatisticRepositoryImpl()
    val calendarDate = MutableLiveData<LocalDate?>()
    var date: LocalDate = LocalDate.now()
        private set

    init {
        setDayStatistic()
    }

    fun getCurrentDayStatistic(): DayStatistic? {
        return dayStatistic.value
    }

    fun getPrevious() {
        date = date.minusDays(1)
        setDayStatistic()
    }

    fun getStatisticByDate(newDate: String) {
        date = LocalDate.parse(newDate)
        setDayStatistic()
    }

    fun getNext() {
        date = date.plusDays(1)
        setDayStatistic()
    }

    private fun setDayStatistic() {
        dayStatistic.value = dayStatisticRepository.getStatisticByDate(date.toString())
    }

}