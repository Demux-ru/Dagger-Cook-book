package com.example.ditest.domain.repositry

import com.example.ditest.domain.entity.Currency
import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {

    fun getList(): Flow<List<Currency>>
    fun saveList(currencies: List<Currency>)
    fun getSavedList(): List<Currency>
}