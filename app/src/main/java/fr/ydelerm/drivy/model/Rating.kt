package fr.ydelerm.drivy.model

import android.os.Parcel
import android.os.Parcelable


data class Rating(
    val average: Float,
    val count: Int
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readFloat(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeFloat(average)
        writeInt(count)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Rating> = object : Parcelable.Creator<Rating> {
            override fun createFromParcel(source: Parcel): Rating = Rating(source)
            override fun newArray(size: Int): Array<Rating?> = arrayOfNulls(size)
        }
    }
}