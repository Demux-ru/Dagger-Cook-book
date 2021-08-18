package com.example.ditest.domain.usecase

import com.example.ditest.domain.entity.Currency
import com.example.ditest.domain.repositry.CurrenciesRepository
import javax.inject.Inject

class GetCurrencyListUseCase @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) {

    suspend operator fun invoke(): List<Currency> =
        currenciesRepository.getList()
}