package com.example.ditest.data

import com.example.ditest.data.model.ResponseModel
import com.example.ditest.domain.entity.Currency
import javax.inject.Inject

class ValuteConvertor @Inject constructor() {

    fun convert(from: ResponseModel): List<Currency> =
        from.currency?.values
            ?.toList()
            ?.map { valuteModel ->
                Currency(
                    iD = valuteModel.iD.orEmpty(),
                    name = valuteModel.name.orEmpty(),
                    numCode = valuteModel.numCode.orEmpty(),
                    charCode = valuteModel.charCode.orEmpty(),
                    nominal = valuteModel.nominal,
                    value = valuteModel.value,
                    previous = valuteModel.previous
                )
            } ?: emptyList()
}