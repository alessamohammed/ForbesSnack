package com.example.jetsnack.repository

import com.example.jetsnack.data.Api.BillionaireApi
import com.example.jetsnack.domain.model.request.Billionaire

class BillionaireRepository_impl  (
    private val billionaireApi: BillionaireApi
) : BillionaireRepository {
    override suspend fun getBillionaires(): List<Billionaire> {
        return billionaireApi.getBillionaires()
    }
}