package com.zhuravlev.stockobserverapp.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zhuravlev.stockobserverapp.model.Stock


@Database(entities = arrayOf(Stock::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDAO
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE stock ADD COLUMN enDescription TEXT DEFAULT '' NOT NULL")
    }
}