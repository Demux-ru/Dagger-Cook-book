package com.example.ditest.domain.repositry

import com.example.ditest.domain.entity.Currency

interface CurrenciesRepository {

    suspend fun getList(): List<Currency>
    fun saveList(currencies: List<Currency>)
    fun getSavedList(): List<Currency>
}