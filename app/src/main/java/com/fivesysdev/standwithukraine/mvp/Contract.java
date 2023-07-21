package com.fivesysdev.standwithukraine.mvp;

import com.fivesysdev.standwithukraine.data.DayStatistic;

import java.time.LocalDate;

public interface Contract {
    interface View {
        void setDayStatistic(DayStatistic dayStatistic);
        void blockingNextButton(boolean isBlocked);
    }
    interface Model {
        DayStatistic getNextDayStatistic();
        DayStatistic getPreviousDayStatistic();
        DayStatistic getCurrentDayStatistic();
        LocalDate getDate();

    }
    interface Presenter {
        void onPreviousButtonClick();
        void onNextButtonClick();
        DayStatistic getDayStatistic();
        void onDestroy();
    }
}
