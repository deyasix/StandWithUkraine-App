package com.fivesysdev.standwithukraine.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import android.content.res.Configuration;
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
        setListeners();
        setRecyclerView();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date", presenter.getDayStatistic().getDate());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter.setDate(savedInstanceState.getString("date"));
    }

    private void setListeners() {
        binding.buttonPrevious.setOnClickListener(v -> presenter.onPreviousButtonClick());
        binding.buttonNext.setOnClickListener(v -> presenter.onNextButtonClick());
    }

    private void setRecyclerView() {
        DayStatistic currentDayStatistic = presenter.getDayStatistic();
        binding.textViewDate.setText(currentDayStatistic.getDate());
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        binding.recyclerView.setAdapter(new StatisticAdapter(currentDayStatistic.getStatistic()));
        DividerItemDecoration verticalDivider = new DividerItemDecoration(binding.recyclerView.getContext(), OrientationHelper.VERTICAL);
        DividerItemDecoration horizontalDivider = new DividerItemDecoration(binding.recyclerView.getContext(), OrientationHelper.HORIZONTAL);
        binding.recyclerView.addItemDecoration(verticalDivider);
        binding.recyclerView.addItemDecoration(horizontalDivider);
    }

    @Override
    public void setDayStatistic(DayStatistic statistic) {
        binding.textViewDate.setText(statistic.getDate());
        binding.recyclerView.setAdapter(new StatisticAdapter(statistic.getStatistic()));
    }

    @Override
    public void blockingNextButton(boolean isBlocked) {
        binding.buttonNext.setEnabled(!isBlocked);
    }
}