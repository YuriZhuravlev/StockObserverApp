package com.zhuravlev.stockobserverapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock")
data class Stock(
    @PrimaryKey
    val symbol: String,
    var imageUrl: String,
    var description: String,
    var star: Boolean,
    var price: String,
    var changePrice: String,
    var boardlist: String = "",
    var enDescription: String = ""
) {
    override fun equals(other: Any?): Boolean {
        return this.symbol == (other as Stock).symbol
    }

    override fun hashCode(): Int {
        return symbol.hashCode()
    }

    fun identical(other: Stock): Boolean {
        return (this.symbol == other.symbol
                && this.changePrice == other.changePrice
                && this.price == other.price
                && this.star == other.star
                && this.imageUrl == other.imageUrl
                && this.boardlist == other.boardlist
                && this.description == other.description
                && this.enDescription == other.enDescription)
    }
}