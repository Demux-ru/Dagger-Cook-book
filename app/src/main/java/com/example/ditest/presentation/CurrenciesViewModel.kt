package com.example.ditest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ditest.domain.usecase.GetCurrencyByIdUseCase
import com.example.ditest.domain.usecase.GetCurrencyListUseCase
import com.example.ditest.domain.usecase.SaveCurrenciesUseCase
import com.example.ditest.presentation.state.CurrenciesState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CurrenciesViewModel @Inject constructor(
	private val getCurrencyListUseCase: GetCurrencyListUseCase,
	private val saveCurrenciesUseCase: SaveCurrenciesUseCase
) : ViewModel() {

	private var disposable: Disposable? = null

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
		disposable = getCurrencyListUseCase()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{ currencies ->
					saveCurrenciesUseCase(currencies)
					_state.value = CurrenciesState.Content(currencies)
					hashCode()
				}, ::proceedError)
	}

	private fun proceedError(throwable: Throwable) {
		_state.value = CurrenciesState.Error
	}

	override fun onCleared() {
		disposable?.dispose()
		super.onCleared()
	}
}