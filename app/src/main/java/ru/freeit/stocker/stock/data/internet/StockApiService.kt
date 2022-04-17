package ru.freeit.stocker.stock.data.internet

import org.json.JSONArray
import ru.freeit.stocker.core.okhttp.OkHttpPrettyClient

class StockApiService(private val client: OkHttpPrettyClient) {

    private val baseUrl = "https://finnhub.io"
    private val token = "c975ddqad3ibs388i580"

    fun stockSymbols() : List<StockSymbolResponse> {
        val jsonString = client.fetchGetJson("$baseUrl/api/v1/stock/symbol?exchange=US&token=$token")
        val jsonArray = JSONArray(jsonString)
        val symbols = mutableListOf<StockSymbolResponse>()
        val size = jsonArray.length()
        for (i in 0 until size) {
            val symbolJson = jsonArray.getJSONObject(i)
            symbols.add(StockSymbolResponse.from(symbolJson))
        }
        return symbols
    }

}