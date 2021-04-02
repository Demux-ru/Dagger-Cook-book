package com.example.ditest.data.datasource

import com.example.ditest.data.api.CurrencyApiClient
import com.example.ditest.data.model.ResponseModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface CurrenciesDataSource {

    fun getList(): Single<ResponseModel>
}

class CurrenciesDataSourceImpl @Inject constructor(
    private val currencyApiClient: CurrencyApiClient
) : CurrenciesDataSource {

    override fun getList(): Single<ResponseModel> =
        currencyApiClient.getList()
            .subscribeOn(Schedulers.io())

}