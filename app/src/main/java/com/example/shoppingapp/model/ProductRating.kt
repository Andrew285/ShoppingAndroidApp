package com.example.shoppingapp.model

import android.os.Parcel
import android.os.Parcelable

data class ProductRating(
    val rate: Float,
    var count: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(rate)
        parcel.writeInt(count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductRating> {
        override fun createFromParcel(parcel: Parcel): ProductRating {
            return ProductRating(parcel)
        }

        override fun newArray(size: Int): Array<ProductRating?> {
            return arrayOfNulls(size)
        }
    }
}