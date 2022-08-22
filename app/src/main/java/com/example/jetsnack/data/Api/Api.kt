package com.example.jetsnack.data.Api

import com.example.jetsnack.domain.model.request.Billionaire
import retrofit2.http.GET

interface Api {

    @GET("?limit=100")
    suspend fun getBillionaires(): List<Billionaire>

}