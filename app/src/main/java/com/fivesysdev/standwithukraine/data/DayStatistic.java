package com.fivesysdev.standwithukraine.data;

import android.util.Pair;

import java.util.HashMap;
import java.util.List;

public class DayStatistic {
    private String date;
    private List<Pair<Integer, Integer>> statistic;
    public DayStatistic(String date, List<Pair<Integer, Integer>> statistic) {
        this.date = date;
        this.statistic = statistic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Pair<Integer, Integer>> getStatistic() {
        return statistic;
    }

    public void setStatistic(List<Pair<Integer, Integer>> statistic) {
        this.statistic = statistic;
    }
}
