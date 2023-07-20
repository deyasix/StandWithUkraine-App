package com.fivesysdev.standwithukraine.mvp;

import com.fivesysdev.standwithukraine.data.DayStatistic;

public interface Contract {
    interface View {
        void setDayStatistic(DayStatistic dayStatistic);
    }
    interface Model {
        DayStatistic getNextDayStatistic();
        DayStatistic getPreviousDayStatistic();
        DayStatistic getCurrentDayStatistic();

    }
    interface Presenter {
        void onPreviousButtonClick();
        void onNextButtonClick();
        DayStatistic getDayStatistic();
        void onDestroy();
    }
}
