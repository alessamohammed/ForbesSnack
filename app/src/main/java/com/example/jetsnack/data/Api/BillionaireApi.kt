package com.example.jetsnack.data.Api

import com.example.jetsnack.domain.model.request.Billionaire
import retrofit2.http.GET
import retrofit2.http.Path

interface BillionaireApi {

    @GET("api/Forbes400?limit=100")
    suspend fun getBillionaires(): List<Billionaire>


    @GET("api/Forbes400/{filter}?limit=100")
    suspend fun getBillionairesByFilter(@Path("filter") filter: String): List<Billionaire>

    // industry
    @GET("api/Forbes400/industries/{filter}?limit=100")
    suspend fun getBillionairesByIndustry(@Path("filter") industry: String): List<Billionaire>
}