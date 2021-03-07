package com.zhuravlev.stockobserverapp.model

/**
 * Прототип класса, потом решу как организовать работу с сервером
 */
data class Stock(
    val symbol: String,
    var imageUrl: String,
    val description: String,
    var star: Boolean,
    var price: String,
    var changePrice: String,
    var boardlist: String = ""
) {
    override fun equals(other: Any?): Boolean {
        return this.symbol == (other as Stock).symbol
    }

    override fun hashCode(): Int {
        return symbol.hashCode()
    }
}