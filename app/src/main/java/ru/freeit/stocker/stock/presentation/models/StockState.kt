package ru.freeit.stocker.stock.presentation.models

import ru.freeit.stocker.core.error.ErrorType

sealed interface StockState {
    class Error(val type: ErrorType) : StockState
    class Success(private val items: List<StockSymbol>) : StockState {
        fun items() = items.sortedBy { it.name() }
    }
    object Loading : StockState
}