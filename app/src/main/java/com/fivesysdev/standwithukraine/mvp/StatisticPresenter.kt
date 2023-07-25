package com.fivesysdev.standwithukraine.mvp

import com.fivesysdev.standwithukraine.data.DayStatistic
import com.fivesysdev.standwithukraine.mvp.Contract.Presenter
import java.time.LocalDate

class StatisticPresenter(private val view: Contract.View, private val model: Contract.Model) :
    Presenter {
    init {
        checkDateForNextButton()
    }

    override fun onPreviousButtonClick() {
        view.setDayStatistic(model.getPreviousDayStatistic())
        checkDateForNextButton()
    }

    override fun onNextButtonClick() {
        view.setDayStatistic(model.getNextDayStatistic())
        checkDateForNextButton()
    }

    override fun getDayStatistic(): DayStatistic? {
        return model.getCurrentDayStatistic()
    }

    private fun checkDateForNextButton() {
        view.blockingNextButton(model.date == LocalDate.now())
    }

    override fun setDate(date: String?) {
        model.date = LocalDate.parse(date)
        view.setDayStatistic(model.getCurrentDayStatistic())
        checkDateForNextButton()
    }
}
