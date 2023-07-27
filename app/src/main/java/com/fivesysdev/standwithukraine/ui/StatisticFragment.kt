package com.fivesysdev.standwithukraine.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.res.Configuration
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.fivesysdev.standwithukraine.R
import com.fivesysdev.standwithukraine.data.DayStatistic
import com.fivesysdev.standwithukraine.databinding.FragmentStatisticBinding
import java.time.LocalDate
import java.time.ZoneId
import java.util.Objects

class StatisticFragment : Fragment(R.layout.fragment_statistic) {

    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: StatisticViewModel by viewModels()

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
        setDayStatistic(viewModel.getCurrentDayStatistic())
    }
    private fun setListeners() {
        with(binding) {
            btnPrevious.setOnClickListener {
                viewModel.getPrevious()
                setDayStatistic(viewModel.getCurrentDayStatistic())
            }
            btnNext.setOnClickListener {
                viewModel.getNext()
                setDayStatistic(viewModel.getCurrentDayStatistic())
            }
            tvDate.setOnClickListener {
                showCalendar()
            }
        }
    }

    private fun showCalendar() {
        val calendar: Calendar = Calendar.getInstance()
        val date = OnDateSetListener { _, year, month, day ->
            calendar[Calendar.YEAR] = year
            calendar[Calendar.MONTH] = month
            calendar[Calendar.DAY_OF_MONTH] = day
            updateDate(calendar)
        }
        DatePickerDialog(
            requireContext(), R.style.MaterialCalendarCustomStyle, date,
            viewModel.date.year,
            viewModel.date.monthValue - 1, viewModel.date.dayOfMonth
        ).show()
    }
    private fun updateDate(calendar: Calendar) {
        val date : LocalDate = calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        viewModel.getStatisticByDate(date.toString())
        setDayStatistic(viewModel.getCurrentDayStatistic())
    }

    private fun setRecyclerView() {
        setLayoutManager()
        setAdapter()
        setRecyclerDividers()
        setEmptyDataObserver()
    }

    private fun setDayStatistic(dayStatistic: DayStatistic?) {
        checkBlockNextButton()
        if (dayStatistic != null) {
            binding.recyclerview.adapter = StatisticAdapter(dayStatistic.getStatisticsPair())
        }
        binding.tvDate.text = viewModel.date.toString()
        setEmptyDataObserver()
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
        getDrawable(requireContext(), R.drawable.divider)?.let { horizontalDivider.setDrawable(it) }
        getDrawable(requireContext(), R.drawable.divider)?.let { verticalDivider.setDrawable(it) }
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
        val currentDayStatistic = viewModel.getCurrentDayStatistic()
        val stats: List<Pair<Int, Int>> = currentDayStatistic?.getStatisticsPair() ?: ArrayList()
        binding.recyclerview.adapter = StatisticAdapter(stats)
    }
}
