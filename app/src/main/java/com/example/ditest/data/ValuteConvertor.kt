package com.example.ditest.data

import com.example.ditest.data.model.ResponseModel
import com.example.ditest.domain.entity.Valute
import javax.inject.Inject

class ValuteConvertor @Inject constructor() {

    fun convert(from: ResponseModel): List<Valute> =
        from.valute?.values
            ?.toList()
            ?.map { valuteModel ->
                Valute(
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