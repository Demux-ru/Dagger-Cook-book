package com.example.ditest.domain.entity

data class Currency(
	var iD: String,
	var numCode: String,
	var charCode: String,
	var nominal: Int,
	var name: String,
	var value: Double,
	var previous: Double
)