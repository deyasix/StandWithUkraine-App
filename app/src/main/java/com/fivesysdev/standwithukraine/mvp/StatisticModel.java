package com.fivesysdev.standwithukraine.mvp;

import com.fivesysdev.standwithukraine.data.StatisticRepository;

import java.time.LocalDate;

public class StatisticModel implements Contract.Model {

    private StatisticRepository statisticRepository;
    private LocalDate date;

    public StatisticModel() {
        this.date = LocalDate.now();
    }

    @Override
    public void getNextDayStatistic() {
        date = date.plusDays(1);
    }

    @Override
    public void getPreviousDayStatistic() {
        date = date.minusDays(1);
    }

}
