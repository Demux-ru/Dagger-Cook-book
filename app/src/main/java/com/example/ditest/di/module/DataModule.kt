package com.example.ditest.di.module

import com.example.ditest.data.datasource.CurrenciesDataSource
import com.example.ditest.data.datasource.CurrenciesDataSourceImpl
import com.example.ditest.data.repository.CurrenciesRepositoryImpl
import com.example.ditest.di.AppScope
import com.example.ditest.domain.repositry.CurrenciesRepository
import dagger.Binds
import dagger.Module

@Module(includes = [ApiModule::class])
interface DataModule {

    @Binds
    @AppScope
    fun bindCurrenciesDataSource(dataSource: CurrenciesDataSourceImpl): CurrenciesDataSource


    @Binds
    @AppScope
    fun bindCurrenciesRepository(repository: CurrenciesRepositoryImpl): CurrenciesRepository
}
