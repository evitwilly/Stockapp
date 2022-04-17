package ru.freeit.stocker.stock.ui.models

object Shimming : StockListItem {
    override fun areContentsTheSame(other: StockListItem) = false
    override fun areItemsTheSame(other: StockListItem) = false
}