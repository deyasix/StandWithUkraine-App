package com.fivesysdev.standwithukraine.data;

import java.util.List;

public interface Repository<T> {
    void addAll(List<T> list);
    void updateStatistics();
    T findByDate(String date);
}
