package fr.ydelerm.drivy.model

import com.google.gson.annotations.SerializedName

data class Car(
    val brand: String,
    val model: String,
    @SerializedName("picture_url") val pictureUrl: String,
    @SerializedName("price_per_day") val pricePerDay: Int,
    val rating: Rating,
    val owner: Owner

)