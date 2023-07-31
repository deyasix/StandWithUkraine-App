package com.fivesysdev.standwithukraine.data

import com.fivesysdev.standwithukraine.data.model.Increase
import com.fivesysdev.standwithukraine.data.model.Stats

data class DayStatistic(
    val date: String, val statistics: Stats, val increase: Increase
) {
    fun getStatisticsPair(): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()
        val statsValues = listOf(
            statistics.personnel_units,
            statistics.tanks,
            statistics.armoured_fighting_vehicles,
            statistics.artillery_systems,
            statistics.mlrs,
            statistics.aa_warfare_systems,
            statistics.planes,
            statistics.helicopters,
            statistics.vehicles_fuel_tanks,
            statistics.warships_cutters,
            statistics.uav_systems,
            statistics.special_military_equip,
            statistics.atgm_srbm_systems,
            statistics.cruise_missiles
        )

        val increasedValues = listOf(
            increase.personnel_units,
            increase.tanks,
            increase.armoured_fighting_vehicles,
            increase.artillery_systems,
            increase.mlrs,
            increase.aa_warfare_systems,
            increase.planes,
            increase.helicopters,
            increase.vehicles_fuel_tanks,
            increase.warships_cutters,
            increase.uav_systems,
            increase.special_military_equip,
            increase.atgm_srbm_systems,
            increase.cruise_missiles
        )

        for (i in statsValues.indices) {
            list.add(Pair(statsValues[i], increasedValues[i]))
        }
        return list
    }
}
