package com.fivesysdev.standwithukraine.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import android.content.res.Configuration;
import java.time.LocalDate;
import android.os.Bundle;
import android.util.Pair;

import com.fivesysdev.standwithukraine.R;
import com.fivesysdev.standwithukraine.data.DayStatistic;
import com.fivesysdev.standwithukraine.databinding.ActivityMainBinding;
import com.fivesysdev.standwithukraine.mvp.Contract;
import com.fivesysdev.standwithukraine.mvp.StatisticModel;
import com.fivesysdev.standwithukraine.mvp.StatisticPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
        outState.putString("date", binding.textViewDate.getText().toString());
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
        List<Pair<Integer, Integer>> stats;
        if (currentDayStatistic == null) {
            stats = new ArrayList<>();
            binding.textViewDate.setText(LocalDate.now().toString());
        } else {
            binding.textViewDate.setText(currentDayStatistic.getDate());
            stats = currentDayStatistic.getStatistic();
        }
        GridLayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 2);
        } else {
            layoutManager = new GridLayoutManager(this, 3);
        }
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(new StatisticAdapter(stats));
        setRecyclerDividers();
        setEmptyDataObserver();
    }

    @Override
    public void setDayStatistic(DayStatistic statistic) {
        if (statistic != null) {
            binding.textViewDate.setText(statistic.getDate());
            binding.recyclerView.setAdapter(new StatisticAdapter(statistic.getStatistic()));
        }
        else binding.textViewDate.setText(LocalDate.now().toString());
        setEmptyDataObserver();
    }

    @Override
    public void blockingNextButton(boolean isBlocked) {
        binding.buttonNext.setEnabled(!isBlocked);
    }

    private void setRecyclerDividers() {
        DividerItemDecoration verticalDivider = new DividerItemDecoration(binding.recyclerView.getContext(),
                OrientationHelper.VERTICAL);
        DividerItemDecoration horizontalDivider = new DividerItemDecoration(binding.recyclerView.getContext(),
                OrientationHelper.HORIZONTAL);
        binding.recyclerView.addItemDecoration(verticalDivider);
        binding.recyclerView.addItemDecoration(horizontalDivider);
    }
    private void setEmptyDataObserver() {
        EmptyStatisticDataObserver emptyDataObserver =
                new EmptyStatisticDataObserver(binding.recyclerView, findViewById(R.id.emptyData));
        Objects.requireNonNull(binding.recyclerView.getAdapter()).registerAdapterDataObserver(emptyDataObserver);
    }
}