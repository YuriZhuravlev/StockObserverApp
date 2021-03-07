package com.zhuravlev.stockobserverapp

import android.app.Application
import com.zhuravlev.stockobserverapp.storage.Storage

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Storage(applicationContext)
    }
}