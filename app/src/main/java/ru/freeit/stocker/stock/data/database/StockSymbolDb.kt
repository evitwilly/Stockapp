package ru.freeit.stocker.stock.data.database

import android.content.ContentValues
import ru.freeit.stocker.stock.presentation.models.StockSymbol

class StockSymbolDb(
    private val id: Long = 0L,
    private val symbol: String,
    private val desc: String,
    private val price: Float = 0f
) {

    fun id() = id.toString()

    fun symbol(content: ContentValues, key: String) {
        content.put(key, symbol)
    }
    fun desc(content: ContentValues, key: String) {
        content.put(key, desc)
    }
    fun price(content: ContentValues, key: String) {
        content.put(key, price)
    }

    fun toUi() = StockSymbol(symbol, desc, price)
}