package com.fivesysdev.standwithukraine.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver

class EmptyStatisticDataObserver(
    private val recyclerView: RecyclerView,
    private val emptyView: View?
) : AdapterDataObserver() {
    init {
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        if (emptyView != null && recyclerView.adapter != null) {
            val emptyViewVisible = recyclerView.adapter?.itemCount == 0
            if (emptyViewVisible) {
                emptyView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }
    }

    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }
}
