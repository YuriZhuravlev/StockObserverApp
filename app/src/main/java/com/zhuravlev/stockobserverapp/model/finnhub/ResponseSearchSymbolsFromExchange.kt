package com.zhuravlev.stockobserverapp.model.finnhub

import com.google.gson.annotations.SerializedName

data class ResponseSearchSymbolsFromExchange(

	@field:SerializedName("displaySymbol")
	val displaySymbol: String? = null,

	@field:SerializedName("symbol")
	val symbol: String? = null,

	@field:SerializedName("mic")
	val mic: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("figi")
	val figi: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)
