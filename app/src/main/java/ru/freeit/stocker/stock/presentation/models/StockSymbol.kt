package ru.freeit.stocker.stock.presentation.models

import android.widget.TextView

class StockSymbol(
    private val symbol: String,
    private val desc: String,
    private val price: Float
) {
    fun bindName(view: TextView) {
        view.text = symbol
    }
    fun bindDesc(view: TextView) {
        view.text = desc
    }

    fun symbolStr() = symbol

    fun matches(keyword: String) = symbol.lowercase().startsWith(keyword.lowercase())

}