package com.example.jetsnack.domain.model.request


import com.google.gson.annotations.SerializedName

data class FinancialAsset(
    @SerializedName("companyName")
    val companyName: String,
    @SerializedName("currencyCode")
    val currencyCode: String,
    @SerializedName("currentPrice")
    val currentPrice: String,
    @SerializedName("exchange")
    val exchange: String,
    @SerializedName("exchangeRate")
    val exchangeRate: String,
    @SerializedName("exerciseOptionPrice")
    val exerciseOptionPrice: String,
    @SerializedName("interactive")
    val interactive: Boolean,
    @SerializedName("numberOfShares")
    val numberOfShares: String,
    @SerializedName("sharePrice")
    val sharePrice: String,
    @SerializedName("ticker")
    val ticker: String
)