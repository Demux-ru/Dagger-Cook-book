package com.example.ditest.data.converter

import com.example.ditest.data.model.CurrencyModel
import com.example.ditest.data.model.ResponseModel
import com.example.ditest.domain.entity.Currency
import javax.inject.Inject

class CurrencyConverter @Inject constructor() {

	private companion object {
		const val DEFAULT_VALUE = 0.0
		const val DEFAULT_NOMINAL = 0
	}

	fun convert(from: ResponseModel): List<Currency> {
		val currenciesModels = from.currency?.values?.toList() ?: emptyList()
		return convertList(currenciesModels)
	}

	fun revertList(to: List<Currency>): List<CurrencyModel> =
		to.map { currency ->
			CurrencyModel().apply {
				id = currency.id
				numCode = currency.numCode
				charCode = currency.charCode
				nominal = currency.nominal
				name = currency.name
				value = currency.value
				previous = currency.previous
			}
		}

	fun convertList(from: List<CurrencyModel>): List<Currency> =
		from.map { currencyModel ->
			Currency(
				id = currencyModel.id.orEmpty(),
				name = currencyModel.name.orEmpty(),
				numCode = currencyModel.numCode.orEmpty(),
				charCode = currencyModel.charCode.orEmpty(),
				nominal = currencyModel.nominal ?: DEFAULT_NOMINAL,
				value = currencyModel.value ?: DEFAULT_VALUE,
				previous = currencyModel.previous ?: DEFAULT_VALUE
			)
		}
}