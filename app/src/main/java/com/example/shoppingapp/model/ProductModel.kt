package com.example.shoppingapp.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class ProductModel(
    val id: Int,
    val title: String?,
    val price: Float,
    val description: String?,
    val images: ArrayList<String>,
    val creationAt: String?,
    val updatedAt: String?,
    val category: CategoryModel?

) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(CategoryModel::class.java.classLoader, CategoryModel::class.java)
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeFloat(price)
        parcel.writeString(description)
        parcel.writeStringList(images)
        parcel.writeString(creationAt)
        parcel.writeString(updatedAt)
        parcel.writeParcelable(category, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }
}