package com.example.jetsnack.domain.model.request


import com.google.gson.annotations.SerializedName

data class Billionaire(
    @SerializedName("abouts")
    val abouts: List<String>,
    @SerializedName("archivedWorth")
    val archivedWorth: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("bioSuppress")
    val bioSuppress: Boolean,
    @SerializedName("bios")
    val bios: List<String>,
    @SerializedName("birthDate")
    val birthDate: Long,
    @SerializedName("city")
    val city: String,
    @SerializedName("countryOfCitizenship")
    val countryOfCitizenship: String,
    @SerializedName("csfDisplayFields")
    val csfDisplayFields: List<String>,
    @SerializedName("date")
    val date: Long,
    @SerializedName("estWorthPrev")
    val estWorthPrev: String,
    @SerializedName("familyList")
    val familyList: Boolean,
    @SerializedName("finalWorth")
    val finalWorth: Double,
    @SerializedName("financialAssets")
    val financialAssets: List<FinancialAsset>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("imageExists")
    val imageExists: Boolean,
    @SerializedName("industries")
    val industries: List<String>,
    @SerializedName("interactive")
    val interactive: Boolean,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("listUri")
    val listUri: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("naturalId")
    val naturalId: String,
    @SerializedName("person")
    val person: Person,
    @SerializedName("personName")
    val personName: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("privateAssetsWorth")
    val privateAssetsWorth: String,
    @SerializedName("rank")
    val rank: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("squareImage")
    val squareImage: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("uri")
    val uri: String,
    @SerializedName("version")
    val version: String,
    @SerializedName("visible")
    val visible: Boolean,
    @SerializedName("wealthList")
    val wealthList: Boolean,
    @SerializedName("year")
    val year: String
)