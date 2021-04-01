package com.example.ditest.domain.repositry

import com.example.ditest.domain.entity.Valute
import io.reactivex.Single

interface ValuteRepository {

    fun getList():Single<List<Valute>>
}