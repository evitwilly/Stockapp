package ru.freeit.stocker.stock.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.stocker.stock.ui.models.Shimming
import ru.freeit.stocker.stock.ui.models.StockListItem
import ru.freeit.stocker.stock.ui.models.StockSymbol

class StockAdapter : ListAdapter<StockListItem, RecyclerView.ViewHolder>(StockDiffUtilItemCallback()) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item is Shimming) shimming else stock
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder =
        if (viewType == shimming) ShimmingViewHolder(parent) else StockViewHolder.from(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) : Unit {
        val item = getItem(position)
        if (holder is StockViewHolder && item is StockSymbol) {
            holder.bind(item)
        }
    }

    fun loading() {
        submitList(List(10) { Shimming })
    }

    fun success(items: List<StockSymbol>) {
        submitList(items)
    }

    companion object {
        private const val shimming = 1
        private const val stock = 2
    }
}