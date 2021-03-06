package com.zhuravlev.stockobserverapp.model.moex

import com.google.gson.annotations.SerializedName

data class ResponsePriceAllStocksByDate(

	@field:SerializedName("history.cursor")
	val historyCursor: HistoryCursor? = null,

	@field:SerializedName("history")
	val history: History? = null
)

data class History(

	@field:SerializedName("data")
	val data: List<String?>? = null,

	@field:SerializedName("columns")
	val columns: List<String?>? = null
)

data class HistoryCursor(

	@field:SerializedName("data")
	val data: List<Int?>? = null,

	@field:SerializedName("columns")
	val columns: List<String?>? = null
)
