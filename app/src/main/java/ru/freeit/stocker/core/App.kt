package ru.freeit.stocker.core

import android.app.Application
import android.content.Context
import okhttp3.OkHttpClient
import ru.freeit.stocker.core.okhttp.OkHttpPrettyClient
import ru.freeit.stocker.stock.data.database.StockDatabase
import ru.freeit.stocker.stock.data.internet.StockApiService
import ru.freeit.stocker.stock.data.repository.StockRepository
import ru.freeit.stocker.stock.presentation.StockViewModelFactory
import java.util.concurrent.TimeUnit

class CoreModule(ctx: Context) {
    private val database by lazy { StockDatabase(ctx) }
    private val service by lazy { StockApiService(OkHttpPrettyClient(OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS).build()))
    }

    fun stockViewModelFactory() = StockViewModelFactory(StockRepository(service, database))
}

class App : Application() {
    val module by lazy { CoreModule(this) }
}