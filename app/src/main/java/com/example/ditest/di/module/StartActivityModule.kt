package com.example.ditest.di.module

import android.app.Activity
import com.example.ditest.di.ActivityScope
import com.example.ditest.di.FragmentScope
import com.example.ditest.ui.CurrenciesFragment
import com.example.ditest.ui.StartActivity
import com.example.ditest.ui.compose.ComposeCurrenciesFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
interface StartActivityModule {

	@Binds
	@ActivityScope
	fun bindStartActivity(activity: StartActivity): Activity

	@FragmentScope
	@ContributesAndroidInjector(modules = [CurrenciesFragmentModule::class])
	fun provideCurrenciesFragment(): CurrenciesFragment

	@FragmentScope
	@ContributesAndroidInjector(modules = [CurrenciesFragmentModule::class])
	fun provideComposeCurrenciesFragment(): ComposeCurrenciesFragment
}