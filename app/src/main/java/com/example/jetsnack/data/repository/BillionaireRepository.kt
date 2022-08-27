package com.example.jetsnack.data.repository

import com.example.jetsnack.domain.model.request.Billionaire

interface BillionaireRepository {
    suspend fun getBillionaires(): List<Billionaire>
    suspend fun getBillionairesByFilter(filter: String): List<Billionaire>
    suspend fun getBillionairesByIndustry(industry: String): List<Billionaire>
}