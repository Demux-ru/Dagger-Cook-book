package com.example.ditest.data.model

import com.google.gson.annotations.SerializedName

class CurrencyModel {
	@SerializedName("ID")
	var id: String? = null

	@SerializedName("NumCode")
	var numCode: String? = null

	@SerializedName("CharCode")
	var charCode: String? = null

	@SerializedName("Nominal")
	var nominal: Int? = null

	@SerializedName("Name")
	var name: String? = null

	@SerializedName("Value")
	var value: Double? = null

	@SerializedName("Previous")
	var previous: Double? = null
}
