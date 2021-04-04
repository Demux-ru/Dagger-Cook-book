package com.example.ditest.data.converter

import com.example.ditest.data.model.ResponseModel
import com.example.ditest.domain.entity.Currency
import javax.inject.Inject

class CurrencyConverter @Inject constructor() {

	private companion object {
		const val DEFAULT_VALUE = 0.0
		const val DEFAULT_NOMINAL = 0
	}

	fun convert(from: ResponseModel): List<Currency> =
		from.currency?.values
			?.toList()
			?.map { currencyModel ->
				Currency(
					iD = currencyModel.id.orEmpty(),
					name = currencyModel.name.orEmpty(),
					numCode = currencyModel.numCode.orEmpty(),
					charCode = currencyModel.charCode.orEmpty(),
					nominal = currencyModel.nominal ?: DEFAULT_NOMINAL,
					value = currencyModel.value ?: DEFAULT_VALUE,
					previous = currencyModel.previous ?: DEFAULT_VALUE
				)
			} ?: emptyList()
}