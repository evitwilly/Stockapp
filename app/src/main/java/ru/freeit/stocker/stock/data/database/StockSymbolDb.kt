package ru.freeit.stocker.stock.data.database

import android.content.ContentValues
import ru.freeit.stocker.stock.presentation.models.StockSymbol

class StockSymbolDb(
    private val id: Long,
    private val symbol: String,
    private val desc: String,
    private val price: Float = 0f
) {

    fun id() = id
    fun name() = symbol
    fun description() = desc
    fun price() = price

    fun ui() = StockSymbol(id, symbol, desc, price)
}