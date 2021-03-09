package com.zhuravlev.stockobserverapp.model.moex

import com.google.gson.annotations.SerializedName

data class ResponseMarketData(

    @field:SerializedName("marketdata")
    val marketdata: List<MarketDataItem?>? = null
)

data class MarketDataItem(

    @field:SerializedName("OPENPERIODPRICE")
    val openPeriodPrice: Int? = null,

    @field:SerializedName("HIGH")
    val high: Double? = null,

    @field:SerializedName("BOARDID")
    val boardId: String? = null,

    @field:SerializedName("OFFERDEPTHT")
    val offerDepthT: Int? = null,

    @field:SerializedName("VALUE")
    val value: Double? = null,

    @field:SerializedName("LASTOFFER")
    val lASTOFFER: Any? = null,

    @field:SerializedName("QTY")
    val qty: Int? = null,

    @field:SerializedName("WAPTOPREVWAPRICE")
    val wapToPrevWaPrice: Int? = null,

    @field:SerializedName("MARKETPRICETODAY")
    val marketPriceToday: Int? = null,

    @field:SerializedName("MARKETPRICE2")
    val marketPrice2: Int? = null,

    @field:SerializedName("CLOSINGAUCTIONVOLUME")
    val closingAuctionVolume: Int? = null,

    @field:SerializedName("ETFSETTLEPRICE")
    val etfSetTlePrice: Any? = null,

    @field:SerializedName("NUMTRADES")
    val numTrades: Int? = null,

    @field:SerializedName("CLOSEPRICE")
    val closePrice: Any? = null,

    @field:SerializedName("TRADINGSESSION")
    val tradingSession: Any? = null,

    @field:SerializedName("HIGHBID")
    val highBid: Any? = null,

    @field:SerializedName("MARKETPRICE")
    val marketPrice: Int? = null,

    @field:SerializedName("LASTTOPREVPRICE")
    val lastToPrevPrice: Double? = null,

    @field:SerializedName("WAPRICE")
    val waPrice: Int? = null,

    @field:SerializedName("OFFERDEPTH")
    val offerDepth: Any? = null,

    @field:SerializedName("ISSUECAPITALIZATION")
    val issueCapitalization: Long? = null,

    @field:SerializedName("LOW")
    val low: Double? = null,

    @field:SerializedName("LAST")
    val last: Int? = null,

    @field:SerializedName("LCURRENTPRICE")
    val lCurrentPrice: Int? = null,

    @field:SerializedName("SECID")
    val secId: String? = null,

    @field:SerializedName("LCLOSEPRICE")
    val lClosePrice: Int? = null,

    @field:SerializedName("UPDATETIME")
    val updateTime: String? = null,

    @field:SerializedName("CLOSINGAUCTIONPRICE")
    val closingAuctionPrice: Int? = null,

    @field:SerializedName("VALTODAY_USD")
    val valTodayUsd: Int? = null,

    @field:SerializedName("ADMITTEDQUOTE")
    val admitTedQuote: Int? = null,

    @field:SerializedName("OFFER")
    val offer: Any? = null,

    @field:SerializedName("SYSTIME")
    val sysTime: String? = null,

    @field:SerializedName("LASTCNGTOLASTWAPRICE")
    val lastCngToLastWapPrice: Int? = null,

    @field:SerializedName("VALTODAY")
    val valToday: Int? = null,

    @field:SerializedName("BIDDEPTHT")
    val bidDepthT: Int? = null,

    @field:SerializedName("SEQNUM")
    val seqNum: Int? = null,

    @field:SerializedName("VALTODAY_RUR")
    val valTodayRur: Int? = null,

    @field:SerializedName("NUMOFFERS")
    val numOffers: Any? = null,

    @field:SerializedName("CHANGE")
    val change: Int? = null,

    @field:SerializedName("BID")
    val bId: Any? = null,

    @field:SerializedName("SPREAD")
    val spread: Int? = null,

    @field:SerializedName("LOWOFFER")
    val lowOffer: Any? = null,

    @field:SerializedName("LASTCHANGEPRCNT")
    val lastChangePrcnt: Double? = null,

    @field:SerializedName("TRADINGSTATUS")
    val tradingStatus: String? = null,

    @field:SerializedName("LASTCHANGE")
    val lastChange: Double? = null,

    @field:SerializedName("VOLTODAY")
    val volToday: Int? = null,

    @field:SerializedName("WAPTOPREVWAPRICEPRCNT")
    val wapToPrevWaPricePrcnt: Double? = null,

    @field:SerializedName("TIME")
    val time: String? = null,

    @field:SerializedName("ISSUECAPITALIZATION_UPDATETIME")
    val issueCapitalizationUpdateTime: String? = null,

    @field:SerializedName("OPEN")
    val open: Int? = null,

    @field:SerializedName("VALUE_USD")
    val valueUsd: Double? = null,

    @field:SerializedName("NUMBIDS")
    val numBIds: Any? = null,

    @field:SerializedName("ETFSETTLECURRENCY")
    val etfSetTleCurrency: Any? = null,

    @field:SerializedName("BIDDEPTH")
    val bIdDepth: Any? = null,

    @field:SerializedName("LASTBID")
    val lastBId: Any? = null,

    @field:SerializedName("PRICEMINUSPREVWAPRICE")
    val priceMinusPrevWaPrice: Int? = null
)
