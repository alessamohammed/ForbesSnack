package com.example.jetsnack.data.repository

import com.example.jetsnack.data.Api.BillionaireApi
import com.example.jetsnack.domain.model.request.Billionaire
import javax.inject.Inject

class BillionaireRepo @Inject constructor(
    private val billionaireApi: BillionaireApi) {

    suspend fun getBillionaires(): List<Billionaire> {
        return billionaireApi.getBillionaires()
    }


}