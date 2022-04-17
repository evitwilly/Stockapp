package ru.freeit.stocker.stock.ui.models

import ru.freeit.stocker.core.view.StockTextView

class StockSymbol(
    private val symbol: String,
    private val desc: String,
    private val price: Float
) : StockListItem {
    fun bindName(view: StockTextView) {
        view.text = symbol
    }
    fun bindDesc(view: StockTextView) {
        view.text = desc
    }

    override fun areContentsTheSame(other: StockListItem): Boolean {
        if (other !is StockSymbol) return false
        return symbol == other.symbol && desc == other.desc && price == other.price
    }

    override fun areItemsTheSame(other: StockListItem): Boolean {
        if (other !is StockSymbol) return false
        return symbol == other.symbol
    }

    fun matches(keyword: String) = symbol.lowercase().startsWith(keyword.lowercase())

}