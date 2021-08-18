package com.example.ditest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ditest.domain.usecase.GetCurrencyListUseCase
import com.example.ditest.domain.usecase.SaveCurrenciesUseCase
import com.example.ditest.presentation.state.CurrenciesState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrenciesViewModel @Inject constructor(
    private val getCurrencyListUseCase: GetCurrencyListUseCase,
    private val saveCurrenciesUseCase: SaveCurrenciesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CurrenciesState>(CurrenciesState.Loading)
    val state: StateFlow<CurrenciesState> = _state

    fun loadCurrencies() {
        getCurrencies()
    }

    fun refreshList() {
        if (_state.value !is CurrenciesState.Content) {
            return
        }

        _state.value = CurrenciesState.Refresh
        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            proceedError(throwable)
        }) {
            val currencies = getCurrencyListUseCase()
            saveCurrenciesUseCase(currencies)
            _state.value = CurrenciesState.Content(currencies)
        }
    }

    private fun proceedError(throwable: Throwable) {
        _state.value = CurrenciesState.Error
    }
}