package com.fivesysdev.standwithukraine.data

import android.util.Pair

data class DayStatistic(
    val date: String,
    val statistic: List<Pair<Int, Int>>
)
