package com.fivesysdev.standwithukraine.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fivesysdev.standwithukraine.data.DayStatistic;
import com.fivesysdev.standwithukraine.databinding.ActivityMainBinding;
import com.fivesysdev.standwithukraine.mvp.Contract;
import com.fivesysdev.standwithukraine.mvp.StatisticModel;
import com.fivesysdev.standwithukraine.mvp.StatisticPresenter;

public class MainActivity extends AppCompatActivity implements Contract.View {

    private ActivityMainBinding binding;

    Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new StatisticPresenter(this, new StatisticModel());
        binding.textViewDate.setText(presenter.getDate());
        setListeners();
    }

    private void setListeners() {
        binding.buttonPrevious.setOnClickListener(v -> presenter.onPreviousButtonClick());
        binding.buttonNext.setOnClickListener(v -> presenter.onNextButtonClick());
    }

    @Override
    public void setDayStatistic(DayStatistic statistic) {
        binding.textViewDate.setText(statistic.getDate());
    }
}