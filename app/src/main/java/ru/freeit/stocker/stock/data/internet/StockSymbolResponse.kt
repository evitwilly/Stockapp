package ru.freeit.stocker.stock.data.internet

import org.json.JSONObject
import ru.freeit.stocker.stock.data.database.StockSymbolDb

class StockSymbolResponse(private val symbol: String, private val desc: String) {
    fun toDatabase() = StockSymbolDb(symbol = symbol, desc = desc)

    companion object {
        fun from(json: JSONObject) : StockSymbolResponse {
            return StockSymbolResponse(json.getString(SYMBOL), json.getString(DESCRIPTION))
        }

        private const val SYMBOL = "symbol"
        private const val DESCRIPTION = "description"
    }
}