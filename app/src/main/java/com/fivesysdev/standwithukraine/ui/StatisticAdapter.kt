package com.fivesysdev.standwithukraine.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fivesysdev.standwithukraine.databinding.StatisticItemBinding

class StatisticAdapter(private val quantities: List<Pair<Int, Int>>?) :
    RecyclerView.Adapter<StatisticAdapter.ViewHolder>() {

    private val names = listOf(
        "personnel units", "tanks", "AFV", "artillery systems", "MLRS", "AA warfare systems",
        "planes", "helicopters", "vehicles and fuel tanks", "warships/cutters", "cruise missiles",
        "UAV systems", "special military equip", "ATGM/SRBM systems"
    )

    class ViewHolder(binding: StatisticItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val textViewName: TextView
        val textViewQuantity: TextView

        init {
            textViewName = binding.tvName
            textViewQuantity = binding.tvQuantity
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: StatisticItemBinding =
            StatisticItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = quantities?.get(position)
        val text = item?.first.toString() + " (+" + item?.second.toString() + ")"
        val name = names[position] + ": "
        viewHolder.textViewQuantity.text = text
        viewHolder.textViewName.text = name
    }

    override fun getItemCount(): Int {
        return quantities?.size?:0
    }
}