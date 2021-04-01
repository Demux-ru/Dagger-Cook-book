package com.example.ditest.di.module

import com.example.ditest.data.datasource.ValuteDataSource
import com.example.ditest.data.datasource.ValuteDataSourceImpl
import com.example.ditest.data.repository.ValuteRepositoryImpl
import com.example.ditest.di.AppScope
import com.example.ditest.domain.repositry.ValuteRepository
import dagger.Binds
import dagger.Module

@Module(includes = [ApiModule::class])
interface DataModule {

    @Binds
    @AppScope
    fun bindValuteDataSource(dataSource: ValuteDataSourceImpl): ValuteDataSource


    @Binds
    @AppScope
    fun bindSomeRepository(repository: ValuteRepositoryImpl): ValuteRepository
}
