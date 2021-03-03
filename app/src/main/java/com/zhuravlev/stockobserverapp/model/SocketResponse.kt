package com.zhuravlev.stockobserverapp.model

import com.google.gson.annotations.SerializedName

data class SocketResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class DataItem(

	@field:SerializedName("p")
	val P: Double? = null,

	@field:SerializedName("s")
	val S: String? = null,

	@field:SerializedName("t")
	val T: Long? = null,

	@field:SerializedName("v")
	val V: Double? = null
)
