package ru.freeit.stocker.stock.ui.models

import ru.freeit.stocker.core.error.ErrorType

sealed interface StockState {
    class Error(type: ErrorType) : StockState
    class Success(val items: List<StockSymbol>) : StockState
    object Loading : StockState
}