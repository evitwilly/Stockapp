package ru.freeit.stocker.stock.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.stocker.stock.presentation.models.StockSymbol

class StockAdapter(private val items: List<StockSymbol>) : RecyclerView.Adapter<StockViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StockViewHolder.from(parent)
    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}