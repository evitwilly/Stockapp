package ru.freeit.stocker.stock.presentation

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.launch
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import ru.freeit.stocker.core.viewmodel.CoreViewModel
import ru.freeit.stocker.stock.data.repository.StockRepository
import ru.freeit.stocker.stock.presentation.adapter.StockWebSocketListener
import ru.freeit.stocker.stock.presentation.models.StockState
import ru.freeit.stocker.stock.presentation.models.StockSymbol

class StockViewModel(private val repo: StockRepository) : CoreViewModel() {

    private var socket: WebSocket? = null
    private var isInternetConnected: Boolean = false
    private val listener = StockWebSocketListener()
    private val cache = mutableListOf<StockSymbol>()

    private val state = MutableLiveData<StockState>()
    fun observe(owner: LifecycleOwner, observer: Observer<StockState>) = state.observe(owner, observer)

    init {
        loadData()
    }

    fun tryAgain() {
        if (isInternetConnected) {
            loadData()
        }
    }

    fun toggleInternetConnection(isConnected: Boolean) {
        this.isInternetConnected = isConnected
    }

    private fun loadData() {
        ui.launch {
            state.value = StockState.Loading
            val result = repo.stockSymbols()
            state.value = result
            if (result is StockState.Success) {
                cache.addAll(result.items())
            }
        }
    }

    fun search(keyword: String) {
        if (cache.isNotEmpty()) {
            val foundedItems = cache.filter { stockSymbol -> stockSymbol.matches(keyword) }
            state.value = StockState.Success(foundedItems)
        }
    }

    suspend fun savePrice(symbol: StockSymbol) = repo.saveStockPrice(symbol)
    suspend fun price(symbol: StockSymbol) : Float = repo.stockPrice(symbol)

    fun webSocketListener() = listener

    fun bindWebSocket() {
        socket = repo.stockPrices(listener)
    }

    fun unbindWebSocket() {
        Log.d("TEST_", "ok -> $socket")
        socket?.cancel()
    }
}