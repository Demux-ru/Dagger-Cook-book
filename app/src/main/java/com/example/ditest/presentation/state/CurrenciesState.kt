package com.example.ditest.presentation.state

import com.example.ditest.domain.entity.Currency

sealed class CurrenciesState {

	data class Content(val currencyList: List<Currency>) : CurrenciesState()

	object Loading : CurrenciesState()

	object Error : CurrenciesState()

}