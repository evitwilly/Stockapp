package ru.freeit.stocker.stock.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.launch
import ru.freeit.stocker.core.viewmodel.CoreViewModel
import ru.freeit.stocker.stock.data.repository.StockRepository
import ru.freeit.stocker.stock.ui.models.StockState
import ru.freeit.stocker.stock.ui.models.StockSymbol

class StockViewModel(private val repo: StockRepository) : CoreViewModel() {

    private val cache = mutableListOf<StockSymbol>()

    private val state = MutableLiveData<StockState>()
    fun observe(owner: LifecycleOwner, observer: Observer<StockState>) = state.observe(owner, observer)

    init {
        ui.launch {
            state.value = StockState.Loading
            val result = repo.stockSymbols()
            state.value = result
            if (result is StockState.Success) {
                cache.addAll(result.items)
            }
        }
    }

    fun search(keyword: String) {
        if (cache.isNotEmpty()) {
            val foundedItems = cache.filter { stockSymbol -> stockSymbol.matches(keyword) }
            state.value = StockState.Success(foundedItems)
        }
    }

}