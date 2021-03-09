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
	val prevAdmitTedQuote: String? = null,

	@field:SerializedName("BOARDID")
	val boardId: String? = null,

	@field:SerializedName("PREVWAPRICE")
	val prevWaPrice: String? = null,

	@field:SerializedName("PREVPRICE")
	val prevPrice: String? = null,

	@field:SerializedName("REMARKS")
	val remarks: Any? = null,

	@field:SerializedName("SECTYPE")
	val secType: String? = null,

	@field:SerializedName("STATUS")
	val status: String? = null,

	@field:SerializedName("ISSUESIZE")
	val issueSize: String? = null,

	@field:SerializedName("LISTLEVEL")
	val listLevel: String? = null,

	@field:SerializedName("PREVDATE")
	val prevDate: String? = null,

	@field:SerializedName("CURRENCYID")
	val currencyId: String? = null,

	@field:SerializedName("DECIMALS")
	val decimals: String? = null,

	@field:SerializedName("LOTSIZE")
	val lotSize: String? = null,

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
	val faceValue: String? = null,

	@field:SerializedName("PREVLEGALCLOSEPRICE")
	val prevLegalClosePrice: String? = null,

	@field:SerializedName("ISIN")
	val isin: String? = null,

	@field:SerializedName("FACEUNIT")
	val faceUnit: String? = null,

	@field:SerializedName("SETTLEDATE")
	val setTleDate: String? = null,

	@field:SerializedName("BOARDNAME")
	val boardName: String? = null,

	@field:SerializedName("MINSTEP")
	val minStep: String? = null,

	@field:SerializedName("SECID")
	val secId: String? = null
)
