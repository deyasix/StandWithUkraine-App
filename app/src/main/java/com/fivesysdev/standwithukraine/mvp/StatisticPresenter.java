package com.fivesysdev.standwithukraine.mvp;

import com.fivesysdev.standwithukraine.data.DayStatistic;

public class StatisticPresenter implements Contract.Presenter{

    private Contract.View view;
    private Contract.Model model;
    public StatisticPresenter(Contract.View view, Contract.Model model) {
        this.view = view;
        this.model = model;
    }
    @Override
    public void onPreviousButtonClick() {
        view.setDayStatistic(model.getPreviousDayStatistic());
    }

    @Override
    public void onNextButtonClick() {
        view.setDayStatistic(model.getNextDayStatistic());
    }

    @Override
    public DayStatistic getDayStatistic() {
        return model.getCurrentDayStatistic();
    }


    @Override
    public void onDestroy() {

    }
}
