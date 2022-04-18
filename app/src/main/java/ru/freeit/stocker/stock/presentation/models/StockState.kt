package ru.freeit.stocker.stock.presentation.models

import ru.freeit.stocker.core.error.ErrorType

sealed interface StockState {
    class Error(val type: ErrorType) : StockState
    class Success(val items: List<StockSymbol>) : StockState
    object Loading : StockState
}