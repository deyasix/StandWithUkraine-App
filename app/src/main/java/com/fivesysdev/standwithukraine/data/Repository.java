package com.fivesysdev.standwithukraine.data;

import java.util.List;

public interface Repository<T> {
    void add(T t);
    List<T> getAll();
    T findByDate(String date);
}
