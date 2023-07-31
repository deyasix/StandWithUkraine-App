package com.fivesysdev.standwithukraine.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.fivesysdev.standwithukraine.data.ext.setVisibility

class EmptyStatisticDataObserver(
    private val recyclerView: RecyclerView, private val emptyView: View?
) : AdapterDataObserver() {
    init {
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        if (emptyView != null && recyclerView.adapter != null) {
            val emptyViewVisible = recyclerView.adapter?.itemCount == 0
            emptyView.setVisibility(emptyViewVisible)
            recyclerView.setVisibility(!emptyViewVisible)
        }
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        super.onItemRangeChanged(positionStart, itemCount)
        checkIfEmpty()
    }

}
