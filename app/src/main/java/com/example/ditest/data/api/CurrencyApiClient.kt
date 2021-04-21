package com.example.ditest.data.api

import com.example.ditest.data.model.ResponseModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface CurrencyApiClient {

	@GET("daily_json.js")
	suspend fun getList(): ResponseModel
}