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

    }

    @Override
    public void onNextButtonClick() {

    }

    @Override
    public void onDestroy() {

    }
}
