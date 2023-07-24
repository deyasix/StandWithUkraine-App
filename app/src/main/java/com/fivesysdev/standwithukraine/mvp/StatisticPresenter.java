package com.fivesysdev.standwithukraine.mvp;


import com.fivesysdev.standwithukraine.data.DayStatistic;

import java.time.LocalDate;

public class StatisticPresenter implements Contract.Presenter {

    private final Contract.View view;
    private final Contract.Model model;
    public StatisticPresenter(Contract.View view, Contract.Model model) {
        this.view = view;
        this.model = model;
        checkDateForNextButton();
    }
    @Override
    public void onPreviousButtonClick() {
        view.setDayStatistic(model.getPreviousDayStatistic());
        checkDateForNextButton();
    }

    @Override
    public void onNextButtonClick() {
        view.setDayStatistic(model.getNextDayStatistic());
        checkDateForNextButton();
    }

    @Override
    public DayStatistic getDayStatistic() {
        return model.getCurrentDayStatistic();
    }


    private void checkDateForNextButton() {
        view.blockingNextButton(model.getDate().equals(LocalDate.now()));
    }

    @Override
    public void setDate(String date) {
        model.setDate(date);
        view.setDayStatistic(model.getCurrentDayStatistic());
        checkDateForNextButton();
    }
}
