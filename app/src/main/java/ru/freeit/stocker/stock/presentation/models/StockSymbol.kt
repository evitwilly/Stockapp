package ru.freeit.stocker.stock.presentation.models

import ru.freeit.stocker.core.view.StockTextView

class StockSymbol(
    private val symbol: String,
    private val desc: String,
    private val price: Float
) {
    fun bindName(view: StockTextView) {
        view.text = symbol
    }
    fun bindDesc(view: StockTextView) {
        view.text = desc
    }

    fun areContentsTheSame(other: StockSymbol): Boolean {
        return symbol == other.symbol
    }

    fun areItemsTheSame(other: StockSymbol): Boolean {
        return symbol == other.symbol
    }

    fun matches(keyword: String) = symbol.lowercase().startsWith(keyword.lowercase())

}