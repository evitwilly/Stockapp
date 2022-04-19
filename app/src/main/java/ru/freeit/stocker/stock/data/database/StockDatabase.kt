package ru.freeit.stocker.stock.data.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class StockDatabase(ctx: Context) : SQLiteOpenHelper(ctx, "stock_database", null, 1) {

    private val database by lazy { writableDatabase }

    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL("create table $TABLE_NAME ($ID integer primary key on conflict replace, $SYMBOL varchar(36) unique on conflict replace, $DESCRIPTION varchar(50), $PRICE real)")
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    @SuppressLint("Range")
    fun stockSymbols() : List<StockSymbolDb> {
        val cursor = database.query(TABLE_NAME, null, null, null, null, null, null)
        val elements = mutableListOf<StockSymbolDb>()
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getLong(cursor.getColumnIndex(ID))
                val symbol = cursor.getString(cursor.getColumnIndex(SYMBOL))
                val desc = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                val price = cursor.getFloat(cursor.getColumnIndex(PRICE))
                elements.add(StockSymbolDb(id, symbol, desc, price))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return elements
    }

    fun saveStockSymbols(symbols: List<StockSymbolDb>) {
        val content = ContentValues()
        database.beginTransaction()
        for (symbol in symbols) {
            content.put(ID, symbol.id())
            content.put(SYMBOL, symbol.name())
            content.put(DESCRIPTION, symbol.description())
            content.put(PRICE, symbol.price())
            database.insert(TABLE_NAME, null, content)
        }
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    fun updateStockSymbol(id: String, price: Float) {
        val content = ContentValues()
        content.put(PRICE, price)
        val result = database.update(TABLE_NAME, content, "id = ?", arrayOf(id))
        Log.d("TEST_", "updated with $price value -> $result")
    }

    companion object {
        private const val TABLE_NAME = "stock"
        private const val ID = "id"
        private const val SYMBOL = "symbol"
        private const val DESCRIPTION = "description"
        private const val PRICE = "price"
    }
}