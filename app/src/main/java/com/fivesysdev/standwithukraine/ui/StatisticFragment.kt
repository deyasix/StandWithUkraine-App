package com.fivesysdev.standwithukraine.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.fivesysdev.standwithukraine.R
import com.fivesysdev.standwithukraine.data.DayStatistic
import com.fivesysdev.standwithukraine.databinding.FragmentStatisticBinding
import java.time.LocalDate
import java.util.Objects

class StatisticFragment : Fragment(R.layout.fragment_statistic) {

    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: StatisticViewModel by viewModels()
    private val data = mutableListOf<Pair<Int, Int>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setRecyclerView()
        observeState()
    }

    private fun observeState() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.emptydata.root.visibility = View.GONE
            }
        }
        viewModel.dayStatistic.observe(viewLifecycleOwner) {
            setDayStatistic(it)
        }
    }

    private fun setListeners() {
        with(binding) {
            btnPrevious.setOnClickListener {
                viewModel.getPrevious()
            }
            btnNext.setOnClickListener {
                viewModel.getNext()
            }
        }
    }

    private fun setRecyclerView() {
        setLayoutManager()
        setAdapter()
        setRecyclerDividers()
        setEmptyDataObserver()
    }

    private fun setDayStatistic(dayStatistic: DayStatistic?) {
        checkBlockNextButton()
        updateDataList(dayStatistic)
        binding.tvDate.text = viewModel.date.toString()
    }

    private fun updateDataList(dayStatistic: DayStatistic?) {
        data.clear()
        data.addAll(dayStatistic?.getStatisticsPair()?:listOf())
        binding.recyclerview.adapter?.notifyItemRangeChanged(0, data.size)
    }

    private fun checkBlockNextButton() {
        val isBlocked = viewModel.date != LocalDate.now()
        binding.btnNext.isEnabled = isBlocked
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
        val currentDayStatistic = viewModel.dayStatistic.value
        data.clear()
        data.addAll(currentDayStatistic?.getStatisticsPair() ?: listOf())
        binding.recyclerview.adapter = StatisticAdapter(data)
    }
}
