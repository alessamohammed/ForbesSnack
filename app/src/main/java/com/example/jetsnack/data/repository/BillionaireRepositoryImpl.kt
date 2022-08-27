package com.example.jetsnack.data.repository

import com.example.jetsnack.data.Api.BillionaireApi
import com.example.jetsnack.domain.model.request.Billionaire

class BillionaireRepositoryImpl(
    private val billionaireApi: BillionaireApi
) : BillionaireRepository {
    override suspend fun getBillionaires(): List<Billionaire> {
        return billionaireApi.getBillionaires()
    }

    override suspend fun getBillionairesByFilter(filter: String): List<Billionaire> {
        return billionaireApi.getBillionairesByFilter(filter)
    }

    // industry
    override suspend fun getBillionairesByIndustry(industry: String): List<Billionaire> {
        return billionaireApi.getBillionairesByIndustry(industry)
    }

}