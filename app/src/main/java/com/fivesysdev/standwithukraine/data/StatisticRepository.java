package com.fivesysdev.standwithukraine.data;

import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public final class StatisticRepository implements Repository<DayStatistic>{

    List<DayStatistic> statistics;

    public StatisticRepository() {
        Callable<List<DayStatistic>> task = () -> {
            Client client = new Client();
            try {
                return client.getStatistics();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        FutureTask<List<DayStatistic>> future = new FutureTask<>(task);
        new Thread(future).start();
        try {
            statistics = future.get();
        } catch (InterruptedException | ExecutionException exception) {
            Log.d("REP", "Interrupted|ExecutionException");
        }
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
