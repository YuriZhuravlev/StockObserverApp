package com.zhuravlev.stockobserverapp.model

/**
 * Прототип класса, потом решу как организовать работу с сервером
 */
data class Stock(
    val title: String,
    val imageUrl: String,
    val description: String,
    var star: Boolean,
    val price: String,
    val changePrice: String
)