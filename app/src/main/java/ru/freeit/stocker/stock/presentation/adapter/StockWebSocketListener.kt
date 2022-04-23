package ru.freeit.stocker.stock.presentation.adapter

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject

typealias ResultNotifier = (Map<String, Float>) -> Unit

class StockWebSocketListener : WebSocketListener() {

    private val resultNotifiers = mutableSetOf<ResultNotifier>()

    private var webSocket: WebSocket? = null

    fun subscribeBySymbol(symbol: String) {
        webSocket?.send("{\"type\":\"subscribe\",\"symbol\":\"$symbol\"}")
    }

    fun addResultNotifier(resultNotifier: ResultNotifier) {
        resultNotifiers.add(resultNotifier)
    }

    fun removeResultNotifier(resultNotifier: ResultNotifier) {
        resultNotifiers.remove(resultNotifier)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        this.webSocket = webSocket
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        this.webSocket = null
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        try {
            val jsonSymbols = JSONObject(text).getJSONArray("data")
            val symbolAndPrices = mutableMapOf<String, Float>()
            for (index in 0 until jsonSymbols.length()) {
                val jsonSymbol = jsonSymbols.getJSONObject(index)
                if (jsonSymbol.has("s") && jsonSymbol.has("p")) {
                    val symbol = jsonSymbol.getString("s")
                    val price = jsonSymbol.getDouble("p")
                    if (symbol.isNotBlank() && price > 0) {
                        symbolAndPrices[symbol] = price.toFloat()
                    }
                }
            }

            resultNotifiers.forEach { it.invoke(symbolAndPrices) }
        } catch (exc: Exception) {}
    }

}