package com.zhuravlev.stockobserverapp.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhuravlev.stockobserverapp.model.Stock

@Database(entities = arrayOf(Stock::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDAO
}