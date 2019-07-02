package fr.ydelerm.drivy.model

import com.google.gson.annotations.SerializedName

data class Owner(
    val name: String,
    @SerializedName("picture_url") val pictureUrl: String,
    val rating: Rating
)