package com.example.jetsnack.domain.model.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FinancialAsset(
    @Json(name = "companyName")
    val companyName: String,
    @Json(name = "currencyCode")
    val currencyCode: String,
    @Json(name = "currentPrice")
    val currentPrice: Double,
    @Json(name = "exchange")
    val exchange: String,
    @Json(name = "exchangeRate")
    val exchangeRate: Int,
    @Json(name = "exerciseOptionPrice")
    val exerciseOptionPrice: Double,
    @Json(name = "interactive")
    val interactive: Boolean,
    @Json(name = "numberOfShares")
    val numberOfShares: Int,
    @Json(name = "sharePrice")
    val sharePrice: Double,
    @Json(name = "ticker")
    val ticker: String
)