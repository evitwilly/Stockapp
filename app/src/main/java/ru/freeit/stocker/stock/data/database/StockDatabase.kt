package ru.freeit.stocker.stock.data.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StockDatabase(ctx: Context) : SQLiteOpenHelper(ctx, "stock_database", null, 1) {

    private val database by lazy { writableDatabase }

    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL("create table $TABLE_NAME ($ID integer primary key autoincrement, $SYMBOL varchar(20), $DESCRIPTION varchar(50), $PRICE real);")
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    @SuppressLint("Range")
    fun stockSymbols() : List<StockSymbolDb> {
        val cursor = database.rawQuery("select * from $TABLE_NAME", null)
        val elements = mutableListOf<StockSymbolDb>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex(ID))
            val symbol = cursor.getString(cursor.getColumnIndex(SYMBOL))
            val desc = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
            val price = cursor.getFloat(cursor.getColumnIndex(PRICE))
            elements.add(StockSymbolDb(id, symbol, desc, price))
        }
        return elements
    }

    fun saveStockSymbols(symbols: List<StockSymbolDb>) {
        val content = ContentValues()
        database.beginTransaction()
        for (symbol in symbols) {
            symbol.symbol(content, SYMBOL)
            symbol.desc(content, DESCRIPTION)
            symbol.price(content, PRICE)
            database.insert(TABLE_NAME, null, content)
        }
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    fun updateStockSymbol(symbol: StockSymbolDb) {
        val content = ContentValues()
        symbol.symbol(content, SYMBOL)
        symbol.desc(content, DESCRIPTION)
        symbol.price(content, PRICE)
        database.update(TABLE_NAME, content, "$ID = ?", arrayOf(symbol.id()))
    }

    companion object {
        private const val TABLE_NAME = "stock"
        private const val ID = "id"
        private const val SYMBOL = "symbol"
        private const val DESCRIPTION = "description"
        private const val PRICE = "price"
    }
}