package com.fivesysdev.standwithukraine.mvp

import com.fivesysdev.standwithukraine.data.DayStatistic
import java.time.LocalDate

interface Contract {
    interface View {
        fun setDayStatistic(dayStatistic: DayStatistic?)
        fun blockingNextButton(isBlocked: Boolean)
    }

    interface Model {
        fun getNextDayStatistic(): DayStatistic?
        fun getPreviousDayStatistic(): DayStatistic?
        fun getCurrentDayStatistic(): DayStatistic?
        var date: LocalDate
    }

    interface Presenter {
        fun onPreviousButtonClick()
        fun onNextButtonClick()
        fun getDayStatistic(): DayStatistic?
        fun setDate(date: String?)
    }
}
