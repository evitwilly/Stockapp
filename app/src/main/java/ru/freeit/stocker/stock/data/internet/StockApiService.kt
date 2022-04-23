package ru.freeit.stocker.stock.data.internet

import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONArray
import org.json.JSONObject
import ru.freeit.stocker.core.okhttp.OkHttpPrettyClient

class StockApiService(private val client: OkHttpPrettyClient) {

    private val baseUrl = "https://finnhub.io"
    private val webSocketBaseUrl = "wss://ws.finnhub.io"
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

    fun stockSymbolPrice(symbol: String) : Float {
        val jsonString = client.fetchGetJson("$baseUrl/api/v1/quote?symbol=$symbol&token=$token")
        return JSONObject(jsonString).getDouble("c").toFloat()
    }

    fun subscribePriceChanges(listener: WebSocketListener) : WebSocket {
        return client.webSocket("$webSocketBaseUrl?token=$token", listener)
    }

}