package ru.freeit.stocker.stock.data.repository

import ru.freeit.stocker.core.coroutines.default
import ru.freeit.stocker.core.okhttp.ServerException
import ru.freeit.stocker.stock.data.database.StockDatabase
import ru.freeit.stocker.stock.data.internet.StockApiService
import ru.freeit.stocker.core.error.ErrorType
import ru.freeit.stocker.stock.ui.models.StockState
import java.net.UnknownHostException

class StockRepository(
    private val service: StockApiService,
    private val database: StockDatabase
) {
    suspend fun stockSymbols() = default {
        try {
            val serverStockSymbols = service.stockSymbols()
            val databaseStockSymbols = serverStockSymbols.map { server -> server.toDatabase() }
            database.saveStockSymbols(databaseStockSymbols)
            StockState.Success(databaseStockSymbols.map { database -> database.toUi() })
        } catch(exc: Exception) {
            val databaseStockSymbols = database.stockSymbols()
            if (databaseStockSymbols.isNotEmpty()) {
                StockState.Success(databaseStockSymbols.map { database -> database.toUi() })
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