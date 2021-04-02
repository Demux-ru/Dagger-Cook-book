package com.example.ditest.data.repository

import com.example.ditest.data.ValuteConvertor
import com.example.ditest.data.datasource.CurrenciesDataSource
import com.example.ditest.domain.entity.Currency
import com.example.ditest.domain.repositry.CurrenciesRepository
import io.reactivex.Single
import javax.inject.Inject

class CurrenciesRepositoryImpl @Inject constructor(
    private val dataSource: CurrenciesDataSource,
    private val convertor: ValuteConvertor
) : CurrenciesRepository {

    override fun getList(): Single<List<Currency>> =
        dataSource.getList()
            .map(convertor::convert)
}