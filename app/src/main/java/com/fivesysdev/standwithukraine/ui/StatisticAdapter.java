package com.fivesysdev.standwithukraine.ui;


import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fivesysdev.standwithukraine.databinding.StatisticItemBinding;

import java.util.Arrays;
import java.util.List;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.ViewHolder> {

    private List<Pair<Integer, Integer>> quantities;
    private List<String> names = Arrays.asList("personnel units", "tanks", "AFV", "artillery systems", "MLRS",
            "AA warfare systems", "planes", "helicopters", "vehicles and fuel tanks",
            "warships/cutters", "UAV systems", "special military equip", "ATGM/SRBM systems",
            "cruise missiles");

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewQuantity;

        public ViewHolder(StatisticItemBinding binding) {
            super(binding.getRoot());
            textViewName = binding.textViewName;
            textViewQuantity = binding.textViewQuantity;
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewQuantity() {
            return textViewQuantity;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public StatisticAdapter(List<Pair<Integer, Integer>> dataSet) {
        quantities = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        StatisticItemBinding binding = StatisticItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Pair<Integer, Integer> item = quantities.get(position);
        String text = item.first + " (+" + item.second + ")";
        String name = names.get(position) + ": ";
        viewHolder.getTextViewQuantity().setText(text);
        viewHolder.getTextViewName().setText(name);
    }

    @Override
    public int getItemCount() {
        return quantities.size();
    }
}
