package com.fivesysdev.standwithukraine.data;

import java.util.Arrays;
import java.util.List;

public class StatisticRepository implements Repository<DayStatistic>{

    List<DayStatistic> statistics;

    public StatisticRepository() {
        statistics = Arrays.asList(
            new DayStatistic("2023-07-20", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)),
                new DayStatistic("2023-07-19", Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18)));
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
