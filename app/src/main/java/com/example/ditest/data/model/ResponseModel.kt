package com.example.ditest.data.model

import com.google.gson.annotations.SerializedName

class ResponseModel {

    @SerializedName("Date")
    var date: String? = null

    @SerializedName("PreviousDate")
    var previousDate: String? = null

    @SerializedName("PreviousURL")
    var previousURL: String? = null

    @SerializedName("Timestamp")
    var timestamp: String? = null

    @SerializedName("Valute")
    var currency: Map<String, CurrencyModel>? = null
}