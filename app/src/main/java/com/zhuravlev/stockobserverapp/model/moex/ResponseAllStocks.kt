package com.zhuravlev.stockobserverapp.model.moex

import com.google.gson.annotations.SerializedName

data class ResponseAllStocks(

	@field:SerializedName("securities")
	val securities: List<SecuritiesItem?>? = null
)

data class SecuritiesItem(

	@field:SerializedName("history_from")
	val historyFrom: String? = null,

	@field:SerializedName("SHORTNAME")
	val shortName: String? = null,

	@field:SerializedName("BOARDID")
	val boardId: String? = null,

	@field:SerializedName("decimals")
	val decimals: Int? = null,

	@field:SerializedName("history_till")
	val historyTill: String? = null,

	@field:SerializedName("SECID")
	val secId: String? = null,

	@field:SerializedName("NAME")
	val name: String? = null
)
