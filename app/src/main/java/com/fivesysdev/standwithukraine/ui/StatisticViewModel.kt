package com.fivesysdev.standwithukraine.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fivesysdev.standwithukraine.data.DayStatistic
import com.fivesysdev.standwithukraine.data.DayStatisticRepositoryImpl
import kotlinx.coroutines.launch
import java.time.LocalDate

class StatisticViewModel : ViewModel() {

    private val _dayStatistic = MutableLiveData<DayStatistic?>()
    private val dayStatisticRepository = DayStatisticRepositoryImpl()
    private val _loading = MutableLiveData(true)
    val calendarDate = MutableLiveData<LocalDate?>()
    val dayStatistic: LiveData<DayStatistic?>
        get() = _dayStatistic
    val loading: LiveData<Boolean>
        get() = _loading
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

    fun getStatisticByDate(newDate: String) {
        date = LocalDate.parse(newDate)
        setDayStatistic()
    }

    private fun setDayStatistic() {
        viewModelScope.launch {
            val value = dayStatisticRepository.getStatisticByDate(date.toString())
            _dayStatistic.value = value
            _loading.value = false
        }
    }
}