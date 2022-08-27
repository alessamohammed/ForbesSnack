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
    val exchangeRate: Double,
    @SerializedName("exerciseOptionPrice")
    val exerciseOptionPrice: String,
    @SerializedName("interactive")
    val interactive: Boolean,
    @SerializedName("numberOfShares")
    val numberOfShares: String,
    @SerializedName("sharePrice")
    val sharePrice: Double,
    @SerializedName("ticker")
    val ticker: String
)