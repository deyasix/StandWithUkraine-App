package com.fivesysdev.standwithukraine.data;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;

import android.util.Log;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public final class DayStatisticRepository implements Repository<DayStatistic>{

    List<DayStatistic> statistics;
    LocalDate fromDate;

    public DayStatisticRepository() {
        statistics = new ArrayList<>();
        fromDate = LocalDate.now().with(firstDayOfMonth());
        updateStatistics();
    }

    @Override
    public void updateStatistics() {
        Callable<List<DayStatistic>> task = () -> {
            Client client = new Client();
            try {
                return client.getStatisticsFromDate(fromDate.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        FutureTask<List<DayStatistic>> future = new FutureTask<>(task);
        new Thread(future).start();
        try {
            addAll(future.get());
        } catch (InterruptedException | ExecutionException exception) {
            Log.d("REP", "Interrupted|ExecutionException");
        }
    }

    @Override
    public DayStatistic findByDate(String date) {
        if (LocalDate.parse(date).isBefore(fromDate)) {
            fromDate = fromDate.minusMonths(1);
            updateStatistics();
        }
        for (DayStatistic statistic: statistics) {
            if (statistic.getDate().equals(date)) {
                return statistic;
            }
        }
        return null;
    }

    @Override
    public void addAll(List<DayStatistic> list) {
        for (DayStatistic item: list) {
            if (statistics.stream().noneMatch(element -> element.isEqual(item))) {
                statistics.add(item);
            }
        }
    }
}
