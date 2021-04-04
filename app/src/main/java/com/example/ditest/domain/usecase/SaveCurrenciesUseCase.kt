package com.example.ditest.domain.usecase

import com.example.ditest.domain.entity.Currency
import com.example.ditest.domain.repositry.CurrenciesRepository
import javax.inject.Inject

class SaveCurrenciesUseCase @Inject constructor(
	private val repository: CurrenciesRepository
) {

	operator fun invoke(currencies: List<Currency>) {
		repository.saveList(currencies)
	}
}