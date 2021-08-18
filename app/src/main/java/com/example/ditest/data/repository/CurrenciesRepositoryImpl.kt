package com.example.ditest.data.repository

import com.example.ditest.data.converter.CurrencyConverter
import com.example.ditest.data.datasource.CurrenciesDataSource
import com.example.ditest.domain.entity.Currency
import com.example.ditest.domain.repositry.CurrenciesRepository
import javax.inject.Inject

class CurrenciesRepositoryImpl @Inject constructor(
    private val dataSource: CurrenciesDataSource,
    private val converter: CurrencyConverter
) : CurrenciesRepository {

    override suspend fun getList(): List<Currency> {
        val currencies = dataSource.getList()
        return converter.convert(currencies)
    }

    override fun saveList(currencies: List<Currency>) {
        dataSource.setCurrenciesList(converter.revertList(currencies))
    }

    override fun getSavedList(): List<Currency> {
        val currencyModels = dataSource.getSavedCurrenciesList()
        return converter.convertList(currencyModels)
    }
}