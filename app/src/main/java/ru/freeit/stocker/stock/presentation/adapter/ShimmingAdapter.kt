package ru.freeit.stocker.stock.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.stocker.core.theme.ThemeManager

class ShimmingAdapter(private val size: Int, private val themeManager: ThemeManager) : RecyclerView.Adapter<ShimmingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShimmingViewHolder(parent, themeManager)
    override fun onBindViewHolder(holder: ShimmingViewHolder, position: Int) {}
    override fun getItemCount() = size
}