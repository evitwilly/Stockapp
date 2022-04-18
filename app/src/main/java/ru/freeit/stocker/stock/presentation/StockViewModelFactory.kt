package ru.freeit.stocker.stock.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.freeit.stocker.stock.data.repository.StockRepository

class StockViewModelFactory(private val repo: StockRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StockViewModel(repo) as T
    }
}