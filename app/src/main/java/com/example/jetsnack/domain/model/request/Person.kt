package com.example.jetsnack.domain.model.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Person(
    @Json(name = "imageExists")
    val imageExists: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "squareImage")
    val squareImage: String,
    @Json(name = "uri")
    val uri: String
)