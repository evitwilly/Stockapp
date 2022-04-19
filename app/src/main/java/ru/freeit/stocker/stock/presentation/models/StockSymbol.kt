package ru.freeit.stocker.stock.presentation.models

import android.util.Log
import android.widget.TextView
import ru.freeit.stocker.R
import ru.freeit.stocker.stock.data.database.StockSymbolDb

class StockSymbol(
    private val id: Long,
    private val symbol: String,
    private val desc: String,
    private var price: Float
) {


    fun bindName(view: TextView) {
        view.text = symbol
    }
    fun bindDesc(view: TextView) {
        view.text = desc
    }
    fun bindPrice(view: TextView) {
        if (price > 0f) {
            view.text = "$price$"
        } else {
            view.setText(R.string.loading)
        }
    }

    fun changePrice(price: Float) {
        this.price = price
    }

    fun isNotEmptyPrice() = price != 0f

    fun id(): String = id.toString()
    fun name() = symbol
    fun price() = price

    fun matches(keyword: String) = symbol.lowercase().startsWith(keyword.lowercase())

}