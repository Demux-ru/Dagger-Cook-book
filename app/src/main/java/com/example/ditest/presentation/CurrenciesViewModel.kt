package com.example.ditest.presentation

import androidx.lifecycle.ViewModel
import com.example.ditest.domain.usecase.GetCurrencyListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CurrenciesViewModel @Inject constructor(
	private val getCurrencyListUseCase: GetCurrencyListUseCase
) : ViewModel() {

	private var disposable: Disposable? = null


	fun loadCurrencies() {
		disposable = getCurrencyListUseCase()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({}, {})
	}

	override fun onCleared() {
		disposable?.dispose()
		super.onCleared()
	}
}