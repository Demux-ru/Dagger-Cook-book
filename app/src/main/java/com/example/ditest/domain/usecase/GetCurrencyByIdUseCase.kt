package com.example.ditest.domain.usecase

import com.example.ditest.domain.entity.Currency
import com.example.ditest.domain.repositry.CurrenciesRepository
import javax.inject.Inject

class GetCurrencyByIdUseCase @Inject constructor(
	private val repository: CurrenciesRepository
) {

	operator fun invoke(id: String): Currency? =
		repository.getSavedList().firstOrNull { it.id == id }
}