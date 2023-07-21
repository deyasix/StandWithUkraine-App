package com.fivesysdev.standwithukraine.mvp;

import com.fivesysdev.standwithukraine.data.DayStatistic;

import java.time.LocalDate;

public class StatisticPresenter implements Contract.Presenter{

    private Contract.View view;
    private Contract.Model model;
    public StatisticPresenter(Contract.View view, Contract.Model model) {
        this.view = view;
        this.model = model;
        checkDateForNextButton();
    }
    @Override
    public void onPreviousButtonClick() {
        view.setDayStatistic(model.getPreviousDayStatistic());
        view.blockingNextButton(false);
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


    @Override
    public void onDestroy() {
    }

    private void checkDateForNextButton() {
        if (model.getDate().equals(LocalDate.now())) {
            view.blockingNextButton(true);
        }
    }
}
