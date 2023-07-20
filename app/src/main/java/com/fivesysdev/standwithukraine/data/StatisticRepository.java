package com.fivesysdev.standwithukraine.data;

import java.util.ArrayList;
import java.util.List;

public class StatisticRepository implements Repository<DayStatistic>{

    List<DayStatistic> statistics;

    public StatisticRepository() {
        statistics = new ArrayList<>();
    }

    @Override
    public void add(DayStatistic dayStatistic) {
        statistics.add(dayStatistic);
    }

    @Override
    public List<DayStatistic> getAll() {
        return statistics;
    }

    @Override
    public DayStatistic findByDate(String date) {
        for (DayStatistic statistic: statistics) {
            if (statistic.getDate().equals(date)) {
                return statistic;
            }
        }
        return null;
    }
}
