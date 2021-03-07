package com.zhuravlev.stockobserverapp.model.moex

import com.google.gson.annotations.SerializedName

data class ResponsePriceAllStocksByDate(

    @field:SerializedName("history.cursor")
    val historyCursor: List<HistoryCursorItem?>? = null,

    @field:SerializedName("history")
    val history: List<HistoryItem?>? = null
)

data class HistoryItem(

    @field:SerializedName("MARKETPRICE3TRADESVALUE")
    val marketPrice3TradesValue: String? = null,

    @field:SerializedName("NUMTRADES")
    val numTrades: String? = null,

    @field:SerializedName("HIGH")
    val high: String? = null,

    @field:SerializedName("BOARDID")
    val boardId: String? = null,

    @field:SerializedName("SHORTNAME")
    val shortName: String? = null,

    @field:SerializedName("ADMITTEDQUOTE")
    val admittedQuote: String? = null,

    @field:SerializedName("TRADINGSESSION")
    val tradingSession: String? = null,

    @field:SerializedName("VOLUME")
    val volume: String? = null,

    @field:SerializedName("WAPRICE")
    val wapPrice: String? = null,

    @field:SerializedName("VALUE")
    val value: String? = null,

    @field:SerializedName("CLOSE")
    val close: String? = null,

    @field:SerializedName("ADMITTEDVALUE")
    val admittedValue: String? = null,

    @field:SerializedName("TRADEDATE")
    val tradeDate: String? = null,

    @field:SerializedName("OPEN")
    val open: String? = null,

    @field:SerializedName("LEGALCLOSEPRICE")
    val legalClosePrice: String? = null,

    @field:SerializedName("LOW")
    val low: Double? = null,

    @field:SerializedName("MP2VALTRD")
    val mp2ValTrd: String? = null,

    @field:SerializedName("MARKETPRICE2")
    val marketPlace2: String? = null,

    @field:SerializedName("SECID")
    val symbol: String? = null,

    @field:SerializedName("MARKETPRICE3")
    val marketPlace3: String? = null,

    @field:SerializedName("WAVAL")
    val waval: String? = null
)

data class HistoryCursorItem(

    @field:SerializedName("TOTAL")
    val tOTAL: Int? = null,

    @field:SerializedName("INDEX")
    val iNDEX: Int? = null,

    @field:SerializedName("PAGESIZE")
    val pAGESIZE: Int? = null
)
