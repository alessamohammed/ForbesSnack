package com.example.jetsnack.repository

import com.example.jetsnack.domain.model.request.Billionaire

interface BillionaireRepository {
    suspend fun getBillionaires(): List<Billionaire>
}