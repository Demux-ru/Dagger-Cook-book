package com.example.ditest.data.datasource

import com.example.ditest.data.api.CurrencyApiClient
import com.example.ditest.data.model.CurrencyModel
import com.example.ditest.data.model.ResponseModel
import com.example.ditest.data.prefs.Preferences
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface CurrenciesDataSource {

	fun getList(): Single<ResponseModel>
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

	override fun getList(): Single<ResponseModel> =
		currencyApiClient.getList()
			.subscribeOn(Schedulers.io())

	override fun setCurrenciesList(currencies: List<CurrencyModel>) {
		preferences.setCurrenciesList(CURRENCIES_KEY, currencies)
	}

	override fun getSavedCurrenciesList(): List<CurrencyModel> =
		preferences.getCurrenciesList(CURRENCIES_KEY)
}