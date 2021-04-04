package com.example.ditest.domain.repositry

import com.example.ditest.domain.entity.Currency
import io.reactivex.Single

interface CurrenciesRepository {

    fun getList():Single<List<Currency>>
    fun saveList(currencies: List<Currency>)
    fun getSavedList(): List<Currency>
}