package com.example.jetsnack.domain.model.request


import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("imageExists")
    val imageExists: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("squareImage")
    val squareImage: String,
    @SerializedName("uri")
    val uri: String
)