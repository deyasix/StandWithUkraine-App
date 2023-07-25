package com.fivesysdev.standwithukraine.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.fivesysdev.standwithukraine.R;
import com.fivesysdev.standwithukraine.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final String STATISTIC_FRAGMENT_TAG = "STATISTIC_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        openStatisticFragment();
        setContentView(binding.getRoot());
    }

    private void openStatisticFragment() {
        StatisticFragment statisticFragment = (StatisticFragment)
                getSupportFragmentManager().findFragmentByTag(STATISTIC_FRAGMENT_TAG);
        if (statisticFragment == null) {
            statisticFragment = new StatisticFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fcv, statisticFragment, STATISTIC_FRAGMENT_TAG)
                .commit();
    }

}