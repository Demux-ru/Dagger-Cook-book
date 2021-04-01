package com.example.ditest.data.datasource

import com.example.ditest.data.api.ValuteApiClient
import com.example.ditest.data.model.ResponseModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface ValuteDataSource {

    fun getList(): Single<ResponseModel>
}

class ValuteDataSourceImpl @Inject constructor(
    private val valuteApiClient: ValuteApiClient
) : ValuteDataSource {

    override fun getList(): Single<ResponseModel> =
        valuteApiClient.getList()
            .subscribeOn(Schedulers.io())

}