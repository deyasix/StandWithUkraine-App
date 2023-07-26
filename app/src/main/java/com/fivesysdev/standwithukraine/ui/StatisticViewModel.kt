package com.fivesysdev.standwithukraine.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fivesysdev.standwithukraine.data.DayStatistic
import com.fivesysdev.standwithukraine.data.DayStatisticRepositoryImpl
import java.time.LocalDate

class StatisticViewModel : ViewModel() {

    private val _dayStatistic = MutableLiveData<DayStatistic?>()
    private val dayStatisticRepository = DayStatisticRepositoryImpl()
    val dayStatistic: LiveData<DayStatistic?>
        get() = _dayStatistic

    var date: LocalDate = LocalDate.now()
        private set

    init {
        setDayStatistic()
    }

    fun getPrevious() {
        date = date.minusDays(1)
        setDayStatistic()
    }

    fun getNext() {
        date = date.plusDays(1)
        setDayStatistic()
    }

    private fun setDayStatistic() {
        _dayStatistic.value = dayStatisticRepository.getStatisticByDate(date.toString())
    }

}