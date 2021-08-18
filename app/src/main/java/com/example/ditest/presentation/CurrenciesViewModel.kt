package com.example.ditest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ditest.domain.usecase.GetCurrencyListUseCase
import com.example.ditest.domain.usecase.SaveCurrenciesUseCase
import com.example.ditest.presentation.state.CurrenciesState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrenciesViewModel @Inject constructor(
    private val getCurrencyListUseCase: GetCurrencyListUseCase,
    private val saveCurrenciesUseCase: SaveCurrenciesUseCase
) : ViewModel() {

    private val _state = MutableLiveData<CurrenciesState>()
    val state: LiveData<CurrenciesState> = _state

    fun loadCurrencies() {
        _state.value = CurrenciesState.Loading

        getCurrencies()
    }

    fun refreshList() {
        if (_state.value !is CurrenciesState.Content) {
            return
        }

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