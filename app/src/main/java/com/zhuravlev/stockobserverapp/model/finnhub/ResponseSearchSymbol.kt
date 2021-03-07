package com.zhuravlev.stockobserverapp.model.finnhub

import com.google.gson.annotations.SerializedName

data class ResponseSearchSymbol(

	@field:SerializedName("result")
	val result: List<ResultItem?>? = null,

	@field:SerializedName("count")
	val count: Int? = null
)

data class ResultItem(

	@field:SerializedName("displaySymbol")
	val displaySymbol: String? = null,

	@field:SerializedName("symbol")
	val symbol: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)
