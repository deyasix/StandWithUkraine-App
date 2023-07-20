package com.fivesysdev.standwithukraine.mvp;

public class StatisticPresenter implements Contract.Presenter{

    private Contract.View view;
    private Contract.Model model;
    public StatisticPresenter(Contract.View view, Contract.Model model) {
        this.view = view;
        this.model = model;
    }
    @Override
    public void onPreviousButtonClick() {
        model.getPreviousDayStatistic();
    }

    @Override
    public void onNextButtonClick() {
    model.getNextDayStatistic();
    }

    @Override
    public void onDestroy() {

    }
}
