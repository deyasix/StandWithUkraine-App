package com.fivesysdev.standwithukraine.mvp;

import android.util.Log;

import com.fivesysdev.standwithukraine.data.DayStatistic;
import com.fivesysdev.standwithukraine.data.StatisticRepository;

import java.time.LocalDate;

public class StatisticModel implements Contract.Model {

    private StatisticRepository statisticRepository;
    private DayStatistic currentStatistic;
    private LocalDate date;

    public StatisticModel() {
        this.date = LocalDate.now();
        this.statisticRepository = new StatisticRepository();
        setCurrentStatistic();
    }

    @Override
    public DayStatistic getNextDayStatistic() {
        date = date.plusDays(1);
        setCurrentStatistic();
        return currentStatistic;
    }

    @Override
    public DayStatistic getPreviousDayStatistic() {
        date = date.minusDays(1);
        setCurrentStatistic();
        return currentStatistic;
    }

    public void setCurrentStatistic() {
        currentStatistic = statisticRepository.findByDate(String.valueOf(date));
    }

    @Override
    public String getDate() {
        return String.valueOf(date);
    }
}
