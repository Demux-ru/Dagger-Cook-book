package com.example.ditest.domain.usecase

import com.example.ditest.domain.entity.Currency
import com.example.ditest.domain.repositry.CurrenciesRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCurrencyListUseCase @Inject constructor(
	private val currenciesRepository: CurrenciesRepository
) {

	operator fun invoke(): Single<List<Currency>> =
		currenciesRepository.getList()
}