package fr.ydelerm.drivy.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Car(
    val brand: String,
    val model: String,
    @SerializedName("picture_url") val pictureUrl: String,
    @SerializedName("price_per_day") val pricePerDay: Int,
    val rating: Rating,
    val owner: Owner

) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readInt(),
        source.readParcelable<Rating>(Rating::class.java.classLoader)!!,
        source.readParcelable<Owner>(Owner::class.java.classLoader)!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(brand)
        writeString(model)
        writeString(pictureUrl)
        writeInt(pricePerDay)
        writeParcelable(rating, 0)
        writeParcelable(owner, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Car> = object : Parcelable.Creator<Car> {
            override fun createFromParcel(source: Parcel): Car = Car(source)
            override fun newArray(size: Int): Array<Car?> = arrayOfNulls(size)
        }
    }
}