package com.fivesysdev.standwithukraine.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.fivesysdev.standwithukraine.R;
import com.fivesysdev.standwithukraine.data.DayStatistic;
import com.fivesysdev.standwithukraine.databinding.FragmentStatisticBinding;
import com.fivesysdev.standwithukraine.mvp.Contract;
import com.fivesysdev.standwithukraine.mvp.StatisticModel;
import com.fivesysdev.standwithukraine.mvp.StatisticPresenter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatisticFragment extends Fragment implements Contract.View {

    private FragmentStatisticBinding binding;

    Contract.Presenter presenter;

    public StatisticFragment() {
        super(R.layout.fragment_statistic);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStatisticBinding.inflate(getLayoutInflater());
        presenter = new StatisticPresenter(this, new StatisticModel());
        if (savedInstanceState != null) {
            presenter.setDate(savedInstanceState.getString("date"));
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
        setRecyclerView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date", binding.tvDate.getText().toString());
    }

    private void setListeners() {
        binding.btnPrevious.setOnClickListener(v -> presenter.onPreviousButtonClick());
        binding.btnNext.setOnClickListener(v -> presenter.onNextButtonClick());
    }

    private void setRecyclerView() {
        setLayoutManager();
        setAdapter();
        setRecyclerDividers();
        setEmptyDataObserver();
    }

    @Override
    public void setDayStatistic(DayStatistic statistic) {
        if (statistic != null) {
            binding.tvDate.setText(statistic.getDate());
            binding.recyclerview.setAdapter(new StatisticAdapter(statistic.getStatistic()));
        }
        else binding.tvDate.setText(LocalDate.now().toString());
        setEmptyDataObserver();
    }

    @Override
    public void blockingNextButton(boolean isBlocked) {
        binding.btnNext.setEnabled(!isBlocked);
    }

    private void setRecyclerDividers() {
        DividerItemDecoration horizontalDivider = new DividerItemDecoration(requireContext(),
                OrientationHelper.VERTICAL);
        DividerItemDecoration verticalDivider = new DividerItemDecoration(requireContext(),
                OrientationHelper.HORIZONTAL);
        binding.recyclerview.addItemDecoration(verticalDivider);
        binding.recyclerview.addItemDecoration(horizontalDivider);
    }
    private void setEmptyDataObserver() {
        EmptyStatisticDataObserver emptyDataObserver =
                new EmptyStatisticDataObserver(binding.recyclerview, binding.emptydata.getRoot());
        Objects.requireNonNull(binding.recyclerview.getAdapter()).registerAdapterDataObserver(emptyDataObserver);
    }
    private void setLayoutManager() {
        GridLayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 3);
        }
        binding.recyclerview.setLayoutManager(layoutManager);
    }
    private void setAdapter() {
        DayStatistic currentDayStatistic = presenter.getDayStatistic();
        List<Pair<Integer, Integer>> stats;
        if (currentDayStatistic == null) {
            stats = new ArrayList<>();
            binding.tvDate.setText(LocalDate.now().toString());
        } else {
            binding.tvDate.setText(currentDayStatistic.getDate());
            stats = currentDayStatistic.getStatistic();
        }
        binding.recyclerview.setAdapter(new StatisticAdapter(stats));
    }
}
