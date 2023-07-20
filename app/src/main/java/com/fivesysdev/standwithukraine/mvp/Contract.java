package com.fivesysdev.standwithukraine.mvp;

public interface Contract {
    interface View {
        void setDayStatistic(StatisticModel statistic);
    }
    interface Model {
        void getNextDayStatistic();
        void getPreviousDayStatistic();

    }
    interface Presenter {
        void onPreviousButtonClick();
        void onNextButtonClick();
        void onDestroy();
    }
}
