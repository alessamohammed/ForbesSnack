package com.example.jetsnack.data.Api

import com.example.jetsnack.domain.model.request.Billionaire
import retrofit2.http.GET

interface BillionaireApi {

    @GET("Forbes400?limit=100")
    suspend fun getBillionaires(): List<Billionaire>

}