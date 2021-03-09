package com.zhuravlev.stockobserverapp.model.moex

import com.google.gson.annotations.SerializedName

data class ResponseMarketData(

    @field:SerializedName("marketdata")
    val marketdata: List<MarketDataItem?>? = null
)

data class MarketDataItem(

    @field:SerializedName("OPENPERIODPRICE")
    val openPeriodPrice: String? = null,

    @field:SerializedName("HIGH")
    val high: String? = null,

    @field:SerializedName("BOARDID")
    val boardId: String? = null,

    @field:SerializedName("OFFERDEPTHT")
    val offerDepthT: String? = null,

    @field:SerializedName("VALUE")
    val value: String? = null,

    @field:SerializedName("LASTOFFER")
    val lASTOFFER: Any? = null,

    @field:SerializedName("QTY")
    val qty: String? = null,

    @field:SerializedName("WAPTOPREVWAPRICE")
    val wapToPrevWaPrice: String? = null,

    @field:SerializedName("MARKETPRICETODAY")
    val marketPriceToday: String? = null,

    @field:SerializedName("MARKETPRICE2")
    val marketPrice2: String? = null,

    @field:SerializedName("CLOSINGAUCTIONVOLUME")
    val closingAuctionVolume: String? = null,

    @field:SerializedName("ETFSETTLEPRICE")
    val etfSetTlePrice: Any? = null,

    @field:SerializedName("NUMTRADES")
    val numTrades: String? = null,

    @field:SerializedName("CLOSEPRICE")
    val closePrice: Any? = null,

    @field:SerializedName("TRADINGSESSION")
    val tradingSession: Any? = null,

    @field:SerializedName("HIGHBID")
    val highBid: Any? = null,

    @field:SerializedName("MARKETPRICE")
    val marketPrice: String? = null,

    @field:SerializedName("LASTTOPREVPRICE")
    val lastToPrevPrice: String? = null,

    @field:SerializedName("WAPRICE")
    val waPrice: String? = null,

    @field:SerializedName("OFFERDEPTH")
    val offerDepth: Any? = null,

    @field:SerializedName("ISSUECAPITALIZATION")
    val issueCapitalization: String? = null,

    @field:SerializedName("LOW")
    val low: String? = null,

    @field:SerializedName("LAST")
    val last: String? = null,

    @field:SerializedName("LCURRENTPRICE")
    val lCurrentPrice: String? = null,

    @field:SerializedName("SECID")
    val secId: String? = null,

    @field:SerializedName("LCLOSEPRICE")
    val lClosePrice: String? = null,

    @field:SerializedName("UPDATETIME")
    val updateTime: String? = null,

    @field:SerializedName("CLOSINGAUCTIONPRICE")
    val closingAuctionPrice: String? = null,

    @field:SerializedName("VALTODAY_USD")
    val valTodayUsd: String? = null,

    @field:SerializedName("ADMITTEDQUOTE")
    val admitTedQuote: String? = null,

    @field:SerializedName("OFFER")
    val offer: Any? = null,

    @field:SerializedName("SYSTIME")
    val sysTime: String? = null,

    @field:SerializedName("LASTCNGTOLASTWAPRICE")
    val lastCngToLastWapPrice: String? = null,

    @field:SerializedName("VALTODAY")
    val valToday: String? = null,

    @field:SerializedName("BIDDEPTHT")
    val bidDepthT: String? = null,

    @field:SerializedName("SEQNUM")
    val seqNum: String? = null,

    @field:SerializedName("VALTODAY_RUR")
    val valTodayRur: String? = null,

    @field:SerializedName("NUMOFFERS")
    val numOffers: Any? = null,

    @field:SerializedName("CHANGE")
    val change: String? = null,

    @field:SerializedName("BID")
    val bId: Any? = null,

    @field:SerializedName("SPREAD")
    val spread: String? = null,

    @field:SerializedName("LOWOFFER")
    val lowOffer: Any? = null,

    @field:SerializedName("LASTCHANGEPRCNT")
    val lastChangePrcnt: String? = null,

    @field:SerializedName("TRADINGSTATUS")
    val tradingStatus: String? = null,

    @field:SerializedName("LASTCHANGE")
    val lastChange: String? = null,

    @field:SerializedName("VOLTODAY")
    val volToday: String? = null,

    @field:SerializedName("WAPTOPREVWAPRICEPRCNT")
    val wapToPrevWaPricePrcnt: String? = null,

    @field:SerializedName("TIME")
    val time: String? = null,

    @field:SerializedName("ISSUECAPITALIZATION_UPDATETIME")
    val issueCapitalizationUpdateTime: String? = null,

    @field:SerializedName("OPEN")
    val open: String? = null,

    @field:SerializedName("VALUE_USD")
    val valueUsd: String? = null,

    @field:SerializedName("NUMBIDS")
    val numBIds: Any? = null,

    @field:SerializedName("ETFSETTLECURRENCY")
    val etfSetTleCurrency: Any? = null,

    @field:SerializedName("BIDDEPTH")
    val bIdDepth: Any? = null,

    @field:SerializedName("LASTBID")
    val lastBId: Any? = null,

    @field:SerializedName("PRICEMINUSPREVWAPRICE")
    val priceMinusPrevWaPrice: String? = null
)
