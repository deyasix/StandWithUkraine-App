package com.fivesysdev.standwithukraine.mvp;

import com.fivesysdev.standwithukraine.data.DayStatistic;

public interface Contract {
    interface View {
        void setDayStatistic(DayStatistic dayStatistic);
    }
    interface Model {
        DayStatistic getNextDayStatistic();
        DayStatistic getPreviousDayStatistic();
        String getDate();

    }
    interface Presenter {
        void onPreviousButtonClick();
        void onNextButtonClick();
        String getDate();
        void onDestroy();
    }
}
