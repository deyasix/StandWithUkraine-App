package com.fivesysdev.standwithukraine.data;

import java.util.List;

public class DayStatistic {
    private String date;
    private List<Integer> statistic;
    public DayStatistic(String date, List<Integer> statistic) {
        this.date = date;
        this.statistic = statistic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Integer> getStatistic() {
        return statistic;
    }

    public void setStatistic(List<Integer> statistic) {
        this.statistic = statistic;
    }
}
