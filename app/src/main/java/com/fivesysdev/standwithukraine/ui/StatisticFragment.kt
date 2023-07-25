package com.fivesysdev.standwithukraine.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.fivesysdev.standwithukraine.R
import com.fivesysdev.standwithukraine.data.DayStatistic
import com.fivesysdev.standwithukraine.databinding.FragmentStatisticBinding
import com.fivesysdev.standwithukraine.mvp.Contract
import com.fivesysdev.standwithukraine.mvp.Contract.Presenter
import com.fivesysdev.standwithukraine.mvp.StatisticModel
import com.fivesysdev.standwithukraine.mvp.StatisticPresenter
import java.time.LocalDate
import java.util.Objects

class StatisticFragment : Fragment(R.layout.fragment_statistic), Contract.View {

    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val presenter: Presenter by lazy {
        StatisticPresenter(this, StatisticModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticBinding.inflate(layoutInflater)
        if (savedInstanceState != null) {
            presenter.setDate(savedInstanceState.getString("date"))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setRecyclerView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("date", binding.tvDate.text.toString())
    }

    private fun setListeners() {
        with(binding) {
            btnPrevious.setOnClickListener { presenter.onPreviousButtonClick() }
            btnNext.setOnClickListener { presenter.onNextButtonClick() }
        }
    }

    private fun setRecyclerView() {
        setLayoutManager()
        setAdapter()
        setRecyclerDividers()
        setEmptyDataObserver()
    }

    override fun setDayStatistic(dayStatistic: DayStatistic?) {
        if (dayStatistic != null) {
            binding.tvDate.text = dayStatistic.date
            binding.recyclerview.adapter = StatisticAdapter(dayStatistic.statistic)
        } else binding.tvDate.text = LocalDate.now().toString()
        setEmptyDataObserver()
    }

    override fun blockingNextButton(isBlocked: Boolean) {
        binding.btnNext.isEnabled = !isBlocked
    }

    private fun setRecyclerDividers() {
        val horizontalDivider = DividerItemDecoration(
            requireContext(),
            OrientationHelper.VERTICAL
        )
        val verticalDivider = DividerItemDecoration(
            requireContext(),
            OrientationHelper.HORIZONTAL
        )
        with(binding.recyclerview) {
            addItemDecoration(verticalDivider)
            addItemDecoration(horizontalDivider)
        }
    }

    private fun setEmptyDataObserver() {
        with(binding) {
            val emptyDataObserver = EmptyStatisticDataObserver(recyclerview, emptydata.root)
            Objects.requireNonNull(recyclerview.adapter)
                .registerAdapterDataObserver(emptyDataObserver)
        }

    }

    private fun setLayoutManager() {
        binding.recyclerview.layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager(context, 2)
            } else {
                GridLayoutManager(context, 3)
            }
    }

    private fun setAdapter() {
        val currentDayStatistic = presenter.getDayStatistic()
        val stats: List<Pair<Int, Int>>
        if (currentDayStatistic == null) {
            stats = ArrayList()
            binding.tvDate.text = LocalDate.now().toString()
        } else {
            binding.tvDate.text = currentDayStatistic.date
            stats = currentDayStatistic.statistic
        }
        binding.recyclerview.adapter = StatisticAdapter(stats)
    }
}
