package com.zhuravlev.stockobserverapp.model

import com.google.gson.annotations.SerializedName

data class Profile(

	@field:SerializedName("finnhubIndustry")
	val finnhubIndustry: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("ticker")
	val ticker: String? = null,

	@field:SerializedName("marketCapitalization")
	val marketCapitalization: Int? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("weburl")
	val weburl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("ipo")
	val ipo: String? = null,

	@field:SerializedName("logo")
	val logo: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("exchange")
	val exchange: String? = null,

	@field:SerializedName("shareOutstanding")
	val shareOutstanding: Double? = null
)
