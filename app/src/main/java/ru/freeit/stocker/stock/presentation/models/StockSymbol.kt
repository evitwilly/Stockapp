package ru.freeit.stocker.stock.presentation.models

import ru.freeit.stocker.core.view.components.StockTextView

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

    fun symbolStr() = symbol

    fun matches(keyword: String) = symbol.lowercase().startsWith(keyword.lowercase())

}