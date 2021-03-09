package com.zhuravlev.stockobserverapp.model.moex

import com.google.gson.annotations.SerializedName

data class ResponseSecurities(

	@field:SerializedName("securities")
	val securities: List<Security?>? = null
)

data class Security(

	@field:SerializedName("LATNAME")
	val latName: String? = null,

	@field:SerializedName("SECNAME")
	val secName: String? = null,

	@field:SerializedName("PREVADMITTEDQUOTE")
	val prevAdmitTedQuote: Int? = null,

	@field:SerializedName("BOARDID")
	val boardId: String? = null,

	@field:SerializedName("PREVWAPRICE")
	val prevWaPrice: Int? = null,

	@field:SerializedName("PREVPRICE")
	val prevPrice: Int? = null,

	@field:SerializedName("REMARKS")
	val remarks: Any? = null,

	@field:SerializedName("SECTYPE")
	val secType: String? = null,

	@field:SerializedName("STATUS")
	val status: String? = null,

	@field:SerializedName("ISSUESIZE")
	val issueSize: Int? = null,

	@field:SerializedName("LISTLEVEL")
	val listLevel: Int? = null,

	@field:SerializedName("PREVDATE")
	val prevDate: String? = null,

	@field:SerializedName("CURRENCYID")
	val currencyId: String? = null,

	@field:SerializedName("DECIMALS")
	val decimals: Int? = null,

	@field:SerializedName("LOTSIZE")
	val lotSize: Int? = null,

	@field:SerializedName("INSTRID")
	val inStrId: String? = null,

	@field:SerializedName("REGNUMBER")
	val regNumber: String? = null,

	@field:SerializedName("MARKETCODE")
	val marketCode: String? = null,

	@field:SerializedName("SHORTNAME")
	val shortName: String? = null,

	@field:SerializedName("SECTORID")
	val sectorId: Any? = null,

	@field:SerializedName("FACEVALUE")
	val faceValue: Int? = null,

	@field:SerializedName("PREVLEGALCLOSEPRICE")
	val prevLegalClosePrice: Int? = null,

	@field:SerializedName("ISIN")
	val isin: String? = null,

	@field:SerializedName("FACEUNIT")
	val faceUnit: String? = null,

	@field:SerializedName("SETTLEDATE")
	val setTleDate: String? = null,

	@field:SerializedName("BOARDNAME")
	val boardName: String? = null,

	@field:SerializedName("MINSTEP")
	val minStep: Double? = null,

	@field:SerializedName("SECID")
	val secId: String? = null
)
