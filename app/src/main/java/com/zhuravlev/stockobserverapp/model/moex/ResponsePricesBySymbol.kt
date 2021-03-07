package com.zhuravlev.stockobserverapp.model.moex

import com.google.gson.annotations.SerializedName

data class ResponsePricesBySymbol(

	@field:SerializedName("candles")
	val candles: List<CandlesItem?>? = null
)

data class CandlesItem(

	@field:SerializedName("volume")
	val volume: Int? = null,

	@field:SerializedName("high")
	val high: Double? = null,

	@field:SerializedName("low")
	val low: Double? = null,

	@field:SerializedName("end")
	val end: String? = null,

	@field:SerializedName("close")
	val close: Double? = null,

	@field:SerializedName("value")
	val value: Double? = null,

	@field:SerializedName("begin")
	val begin: String? = null,

	@field:SerializedName("open")
	val open: Double? = null
)
