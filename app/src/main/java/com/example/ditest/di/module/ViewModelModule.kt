package com.example.ditest.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.ditest.presentation.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface ViewModelModule {

	@Binds
	@Reusable
	fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}