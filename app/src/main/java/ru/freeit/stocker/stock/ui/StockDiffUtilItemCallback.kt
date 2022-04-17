package ru.freeit.stocker.stock.ui

import androidx.recyclerview.widget.DiffUtil
import ru.freeit.stocker.stock.ui.models.StockListItem

class StockDiffUtilItemCallback : DiffUtil.ItemCallback<StockListItem>() {
    override fun areContentsTheSame(oldItem: StockListItem, newItem: StockListItem): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }

    override fun areItemsTheSame(oldItem: StockListItem, newItem: StockListItem): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }
}