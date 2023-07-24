package com.fivesysdev.standwithukraine.ui;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class EmptyStatisticDataObserver extends RecyclerView.AdapterDataObserver {

    private final View emptyView;
    private final RecyclerView recyclerView;

    public EmptyStatisticDataObserver(RecyclerView recyclerView, View view) {
        this.emptyView = view;
        this.recyclerView = recyclerView;
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if (emptyView != null && recyclerView.getAdapter() != null) {
            boolean emptyViewVisible = recyclerView.getAdapter().getItemCount() == 0;
            if (emptyViewVisible) {
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onChanged() {
        super.onChanged();
        checkIfEmpty();
    }

}
