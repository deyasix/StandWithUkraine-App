package com.fivesysdev.standwithukraine.data;

import android.util.Pair;

import java.util.List;
import java.util.Objects;

public class DayStatistic {
    private final String date;
    private final List<Pair<Integer, Integer>> statistic;
    public DayStatistic(String date, List<Pair<Integer, Integer>> statistic) {
        this.date = date;
        this.statistic = statistic;
    }

    public String getDate() {
        return date;
    }

    public List<Pair<Integer, Integer>> getStatistic() {
        return statistic;
    }

    public boolean isEqual(DayStatistic dayStatistic) {
        return Objects.equals(this.date, dayStatistic.date);
    }
}
