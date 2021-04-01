package com.example.ditest.data.api

import com.example.ditest.data.model.ResponseModel
import io.reactivex.Single
import retrofit2.http.GET

interface ValuteApiClient {

    @GET("daily_json.js")
    fun getList(): Single<ResponseModel>
}