package com.example.ditest.data.model

import java.time.OffsetDateTime

data class ResponseModel(
    var date: OffsetDateTime?,
    var previousDate: OffsetDateTime?,
    var previousURL: String?,
    var timestamp: OffsetDateTime?,
    var valute: Map<String, ValuteModel>?
)