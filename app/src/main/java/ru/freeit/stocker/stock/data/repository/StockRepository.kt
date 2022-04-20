package ru.freeit.stocker.stock.data.repository

import android.util.Log
import kotlinx.coroutines.launch
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import ru.freeit.stocker.core.coroutines.default
import ru.freeit.stocker.core.okhttp.ServerException
import ru.freeit.stocker.stock.data.database.StockDatabase
import ru.freeit.stocker.stock.data.internet.StockApiService
import ru.freeit.stocker.core.error.ErrorType
import ru.freeit.stocker.stock.presentation.models.StockState
import ru.freeit.stocker.stock.presentation.models.StockSymbol
import java.net.UnknownHostException

class StockRepository(
    private val service: StockApiService,
    private val database: StockDatabase
) {

    fun stockPrices(listener: WebSocketListener) : WebSocket {
        return service.subscribePriceChanges(listener)
    }

    suspend fun saveStockPrice(symbol: StockSymbol) = default {
        if (symbol.isNotEmptyPrice()) {
            database.updateStockSymbol(symbol.id(), symbol.price())
        }
    }

    suspend fun stockPrice(symbol: StockSymbol) = default {
        try {
            val price = service.stockSymbolPrice(symbol.name())

            if (price > 0f) {
                database.updateStockSymbol(symbol.id(), price)
            }
            price
        } catch (exc: Exception) {
            0f
        }
    }

    suspend fun stockSymbols() = default {
        try {
            val serverStockSymbols = service.stockSymbols()
            val databaseStockSymbols = serverStockSymbols.mapIndexed { index, server -> server.toDatabase(index + 1L) }
            launch { database.saveStockSymbols(databaseStockSymbols) }
            StockState.Success(databaseStockSymbols.map { database -> database.ui() })
        } catch(exc: Exception) {
            val databaseStockSymbols = database.stockSymbols()
            if (databaseStockSymbols.isNotEmpty()) {
                StockState.Success(databaseStockSymbols.map { database -> database.ui() })
            } else {
                when (exc) {
                    is UnknownHostException -> StockState.Error(ErrorType.MISSING_INTERNET)
                    is ServerException -> StockState.Error(ErrorType.SERVER_ERROR)
                    else -> StockState.Error(ErrorType.HAPPEN_WHAT_IS_NOT_SO)
                }
            }
        }
    }
}