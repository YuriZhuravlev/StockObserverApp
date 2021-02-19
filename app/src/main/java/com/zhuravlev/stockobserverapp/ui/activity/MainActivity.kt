package com.zhuravlev.stockobserverapp.ui.activity

import android.os.Bundle
import com.zhuravlev.stockobserverapp.R
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}