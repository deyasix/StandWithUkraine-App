package com.fivesysdev.standwithukraine.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import android.content.res.Configuration
import android.icu.util.Calendar
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
import com.fivesysdev.standwithukraine.data.ext.setVisibility
import com.fivesysdev.standwithukraine.databinding.FragmentStatisticBinding
import java.time.LocalDate
import java.time.ZoneId
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
        setCalendar()
    }

    private fun setCalendar() {
        if (viewModel.calendarDate.value != null) {
            showCalendar()
        }
    }

    private fun observeState() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.emptydata.root.setVisibility(false)
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
            tvDate.setOnClickListener {
                showCalendar()
            }
        }
    }

    private fun showCalendar() {
        val calendar: Calendar = Calendar.getInstance()
        val onDateChange = OnDateSetListener { _, year, month, day ->
            calendar[Calendar.YEAR] = year
            calendar[Calendar.MONTH] = month
            calendar[Calendar.DAY_OF_MONTH] = day
            updateDate(calendar)
        }
        with(viewModel) {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.MaterialCalendarCustomStyle,
                onDateChange,
                calendarDate.value?.year ?: date.year,
                (calendarDate.value?.monthValue ?: date.monthValue) - 1,
                calendarDate.value?.dayOfMonth ?: date.dayOfMonth
            )
            setDatePicker(datePickerDialog, calendar)
        }
    }

    private fun setDatePicker(datePickerDialog: DatePickerDialog, calendar: Calendar) {
        with(datePickerDialog) {
            datePicker.setOnDateChangedListener { _, year, month, day ->
                viewModel.calendarDate.value = LocalDate.of(year, month + 1, day)
            }
            datePicker.minDate = getMinDate()
            datePicker.maxDate = calendar.timeInMillis
            setOnCancelListener {
                viewModel.calendarDate.value = null
            }
            show()
        }
    }

    private fun getMinDate(): Long {
        with(Calendar.getInstance()) {
            this[Calendar.MONTH] = MIN_MONTH - 1
            this[Calendar.YEAR] = MIN_YEAR
            this[Calendar.DAY_OF_MONTH] = MIN_DAY
            return timeInMillis
        }
    }

    private fun updateDate(calendar: Calendar) {
        val date: LocalDate = calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        viewModel.getStatisticByDate(date.toString())
        viewModel.calendarDate.value = null
        setDayStatistic(viewModel.dayStatistic.value)
    }

    private fun updateDataList(dayStatistic: DayStatistic?) {
        data.clear()
        data.addAll(dayStatistic?.getStatisticsPair() ?: listOf())
        binding.recyclerview.adapter?.notifyItemRangeChanged(0, data.size)
    }

    private fun setRecyclerView() {
        setLayoutManager()
        setAdapter()
        setRecyclerDividers()
        setEmptyDataObserver()
    }

    private fun setDayStatistic(dayStatistic: DayStatistic?) {
        checkBlockButtons()
        updateDataList(dayStatistic)
        binding.tvDate.text = viewModel.date.toString()
    }

    private fun checkBlockButtons() {
        val isNextBlocked = viewModel.date != LocalDate.now()
        val isPrevBlocked = viewModel.date != LocalDate.of(MIN_YEAR, MIN_MONTH, MIN_DAY)
        binding.btnPrevious.isEnabled = isPrevBlocked
        binding.btnNext.isEnabled = isNextBlocked
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
        getDrawable(requireContext(), R.drawable.divider)?.let {
            horizontalDivider.setDrawable(it)
            verticalDivider.setDrawable(it)
        }
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

    companion object {
        const val MIN_DAY = 27
        const val MIN_YEAR = 2022
        const val MIN_MONTH = 2
    }
}
