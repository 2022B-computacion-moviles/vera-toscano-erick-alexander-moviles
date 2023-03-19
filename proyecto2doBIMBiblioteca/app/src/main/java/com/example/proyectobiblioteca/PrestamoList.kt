package com.example.proyectobiblioteca

import android.os.Parcel
import android.os.Parcelable

class PrestamoList(
    var idPrestamoLista: String,
    var nombrePrestamoLista: String,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idPrestamoLista)
        parcel.writeString(nombrePrestamoLista)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PrestamoList> {
        override fun createFromParcel(parcel: Parcel): PrestamoList {
            return PrestamoList(parcel)
        }

        override fun newArray(size: Int): Array<PrestamoList?> {
            return arrayOfNulls(size)
        }
    }
}