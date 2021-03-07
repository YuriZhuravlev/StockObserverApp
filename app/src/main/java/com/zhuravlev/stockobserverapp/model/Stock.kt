package com.zhuravlev.stockobserverapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock")
data class Stock(
    @PrimaryKey
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