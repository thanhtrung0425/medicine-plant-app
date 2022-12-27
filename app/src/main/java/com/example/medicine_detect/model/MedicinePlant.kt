package com.example.medicine_detect.model

import com.google.gson.annotations.SerializedName

data class MedicinePlant(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("sub_title")
    val sub_title: String,
    @SerializedName("science_name")
    val science_name: String,
    @SerializedName("plant_family")
    val plant_family: String,
    @SerializedName("plant_function")
    val plant_function: String,
    @SerializedName("dosage_usage")
    val dosage_usage: String,
    @SerializedName("image_url")
    val image_url: String,
)
