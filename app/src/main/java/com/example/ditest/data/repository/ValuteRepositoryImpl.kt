package com.example.ditest.data.repository

import com.example.ditest.data.ValuteConvertor
import com.example.ditest.data.datasource.ValuteDataSource
import com.example.ditest.domain.entity.Valute
import com.example.ditest.domain.repositry.ValuteRepository
import io.reactivex.Single
import javax.inject.Inject

class ValuteRepositoryImpl @Inject constructor(
    private val dataSource: ValuteDataSource,
    private val convertor: ValuteConvertor
) : ValuteRepository {

    override fun getList(): Single<List<Valute>> =
        dataSource.getList()
            .map(convertor::convert)
}