package com.fivesysdev.standwithukraine.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatisticRepository implements Repository<DayStatistic>{

    List<DayStatistic> statistics;

    public StatisticRepository() {
        statistics = Arrays.asList(
            new DayStatistic("2023-07-20", new ArrayList<>()),
                new DayStatistic("2023-07-19", new ArrayList<>()));
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
