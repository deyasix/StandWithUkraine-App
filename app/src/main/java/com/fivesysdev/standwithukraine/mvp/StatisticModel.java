package com.fivesysdev.standwithukraine.mvp;

import android.util.Log;

import com.fivesysdev.standwithukraine.data.DayStatistic;
import com.fivesysdev.standwithukraine.data.StatisticRepository;

import java.time.LocalDate;

public class StatisticModel implements Contract.Model {

    private final StatisticRepository statisticRepository;
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

    @Override
    public DayStatistic getCurrentDayStatistic() {
        return currentStatistic;
    }

    public void setCurrentStatistic() {
        DayStatistic foundStatic = statisticRepository.findByDate(String.valueOf(date));
        if (foundStatic != null) {
            currentStatistic = foundStatic;
        }
        Log.d("SET1", currentStatistic.getDate());
        Log.d("SET2", date.toString());
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = LocalDate.parse(date);
        setCurrentStatistic();
    }
}
