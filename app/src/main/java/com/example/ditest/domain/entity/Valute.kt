package com.example.ditest.domain.entity

data class Valute(
    var iD: String,
    var numCode: String,
    var charCode: String,
    var nominal: Long,
    var name: String,
    var value: Long,
    var previous: Long
)