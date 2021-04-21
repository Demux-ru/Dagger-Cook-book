package com.example.ditest.domain.usecase

import com.example.ditest.domain.entity.Currency
import com.example.ditest.domain.repositry.CurrenciesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyListUseCase @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) {

    operator fun invoke(): Flow<List<Currency>> =
        currenciesRepository.getList()
}