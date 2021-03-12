package com.zhuravlev.stockobserverapp.model

import java.util.*

class PriceChart(val stock: Stock) {
    class Item(
        var lowPrice: Double,
        var highPrice: Double,
        /**
         * Оборот акций/количество продаж/покупок
         */
        var volume: Int,
        var date: Date
    ) : Comparable<Item> {
        override fun compareTo(other: Item): Int {
            return if (this.date.after(other.date)) {
                -1
            } else {
                if (this.date.before(other.date)) {
                    1
                } else {
                    0
                }
            }
        }
    }

    val list = sortedSetOf<Item>()
}