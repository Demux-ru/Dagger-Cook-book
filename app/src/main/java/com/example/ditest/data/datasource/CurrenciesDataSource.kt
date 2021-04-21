package com.example.ditest.data.datasource

import com.example.ditest.data.api.CurrencyApiClient
import com.example.ditest.data.model.CurrencyModel
import com.example.ditest.data.model.ResponseModel
import com.example.ditest.data.prefs.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface CurrenciesDataSource {

	fun getList(): Flow<ResponseModel>
	fun setCurrenciesList(currencies: List<CurrencyModel>)
	fun getSavedCurrenciesList(): List<CurrencyModel>
}

class CurrenciesDataSourceImpl @Inject constructor(
	private val currencyApiClient: CurrencyApiClient,
	private val preferences: Preferences
) : CurrenciesDataSource {

	private companion object {
		const val CURRENCIES_KEY = "CURRENCIES_KEY"
	}

	override fun getList(): Flow<ResponseModel> =
		flow { emit(currencyApiClient.getList()) }
			.flowOn(Dispatchers.IO)

	override fun setCurrenciesList(currencies: List<CurrencyModel>) {
		preferences.setCurrenciesList(CURRENCIES_KEY, currencies)
	}

	override fun getSavedCurrenciesList(): List<CurrencyModel> =
		preferences.getCurrenciesList(CURRENCIES_KEY)
}