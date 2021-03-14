package com.zhuravlev.stockobserverapp

import android.app.Application
import androidx.room.Room
import com.zhuravlev.stockobserverapp.storage.Storage
import com.zhuravlev.stockobserverapp.storage.database.AppDatabase
import com.zhuravlev.stockobserverapp.storage.database.MIGRATION_1_2

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-stock-observer"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
        Storage(database)
    }
}