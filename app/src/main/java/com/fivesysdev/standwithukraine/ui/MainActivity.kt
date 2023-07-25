package com.fivesysdev.standwithukraine.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fivesysdev.standwithukraine.R
import com.fivesysdev.standwithukraine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openStatisticFragment()
        setContentView(binding.root)
    }

    private fun openStatisticFragment() {
        val statisticFragment = (supportFragmentManager.findFragmentByTag(STATISTIC_FRAGMENT_TAG)
                as StatisticFragment?)?: StatisticFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv, statisticFragment, STATISTIC_FRAGMENT_TAG)
            .commit()
    }

    companion object {
        private const val STATISTIC_FRAGMENT_TAG = "STATISTIC_FRAGMENT_TAG"
    }
}