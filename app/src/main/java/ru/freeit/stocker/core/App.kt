package ru.freeit.stocker.core

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import okhttp3.OkHttpClient
import ru.freeit.stocker.core.okhttp.OkHttpPrettyClient
import ru.freeit.stocker.stock.data.database.StockDatabase
import ru.freeit.stocker.stock.data.internet.StockApiService
import ru.freeit.stocker.stock.data.repository.StockRepository
import ru.freeit.stocker.stock.ui.StockViewModelFactory

class CoreModule(ctx: Context) {
    private val database by lazy { StockDatabase(ctx) }
    private val service by lazy { StockApiService(OkHttpPrettyClient(OkHttpClient())) }

    fun stockViewModelFactory() = StockViewModelFactory(StockRepository(service, database))
}

class App : Application() {
    val module by lazy { CoreModule(this) }
}