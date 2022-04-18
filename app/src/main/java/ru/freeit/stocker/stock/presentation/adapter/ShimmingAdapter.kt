package ru.freeit.stocker.stock.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ShimmingAdapter(private val size: Int) : RecyclerView.Adapter<ShimmingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShimmingViewHolder(parent)
    override fun onBindViewHolder(holder: ShimmingViewHolder, position: Int) {}
    override fun getItemCount() = size
}