package com.example.jetsnack.domain.model.request


import com.google.gson.annotations.SerializedName

data class FinancialAsset(
    @SerializedName("companyName")
    val companyName: String,
    @SerializedName("currencyCode")
    val currencyCode: String,
    @SerializedName("currentPrice")
    val currentPrice: Double,
    @SerializedName("exchange")
    val exchange: String,
    @SerializedName("exchangeRate")
    val exchangeRate: Int,
    @SerializedName("exerciseOptionPrice")
    val exerciseOptionPrice: Double,
    @SerializedName("interactive")
    val interactive: Boolean,
    @SerializedName("numberOfShares")
    val numberOfShares: Int,
    @SerializedName("sharePrice")
    val sharePrice: Double,
    @SerializedName("ticker")
    val ticker: String
)