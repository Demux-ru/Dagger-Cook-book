package com.example.ditest.data.repository

import com.example.ditest.data.converter.CurrencyConverter
import com.example.ditest.data.datasource.CurrenciesDataSource
import com.example.ditest.domain.entity.Currency
import com.example.ditest.domain.repositry.CurrenciesRepository
import io.reactivex.Single
import javax.inject.Inject

class CurrenciesRepositoryImpl @Inject constructor(
	private val dataSource: CurrenciesDataSource,
	private val converter: CurrencyConverter
) : CurrenciesRepository {

	override fun getList(): Single<List<Currency>> =
		dataSource.getList()
			.map(converter::convert)

	override fun saveList(currencies: List<Currency>) {
		dataSource.setCurrenciesList(converter.revertList(currencies))
	}

	override fun getSavedList(): List<Currency> {
		val currencyModels = dataSource.getSavedCurrenciesList()
		return converter.convertList(currencyModels)
	}
}