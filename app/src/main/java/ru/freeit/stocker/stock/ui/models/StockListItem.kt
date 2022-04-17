package ru.freeit.stocker.stock.ui.models

sealed interface StockListItem {
    fun areItemsTheSame(other: StockListItem) : Boolean
    fun areContentsTheSame(other: StockListItem) : Boolean
}
