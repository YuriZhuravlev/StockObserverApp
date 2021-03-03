package com.zhuravlev.stockobserverapp.model

/**
 * Прототип класса, потом решу как организовать работу с сервером
 */
data class Stock(
    val title: String,
    var imageUrl: String,
    val description: String,
    var star: Boolean,
    var price: String,
    var changePrice: String
)