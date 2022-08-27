package com.example.jetsnack.data.repository

import com.example.jetsnack.data.Api.BillionaireApi
import com.example.jetsnack.domain.model.request.Billionaire
import javax.inject.Inject

interface BillionaireRepository {
    suspend fun getBillionaires(): List<Billionaire>
    suspend fun getBillionairesByFilter(filter: String): List<Billionaire>
}