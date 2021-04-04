package com.example.ditest.di.module

import androidx.lifecycle.ViewModel
import com.example.ditest.di.ViewModelKey
import com.example.ditest.presentation.CurrenciesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CurrenciesFragmentModule {

	@Binds
	@IntoMap
	@ViewModelKey(CurrenciesViewModel::class)
	fun bindCurrenciesViewModel(viewModel: CurrenciesViewModel): ViewModel
}