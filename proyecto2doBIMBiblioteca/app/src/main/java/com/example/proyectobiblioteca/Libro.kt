package com.example.proyectobiblioteca

import android.os.Parcel
import android.os.Parcelable

class Libro (
    var idLibro: String,
    var nombreLibro: String,
    var fechaLibro: String,
    var autorLibro: String,
): Parcelable {
    constructor(parcel: Parcel): this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ){
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idLibro)
        parcel.writeString(nombreLibro)
        parcel.writeString(fechaLibro)
        parcel.writeString(autorLibro)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Libro> {
        override fun createFromParcel(parcel: Parcel): Libro {
            return Libro(parcel)
        }

        override fun newArray(size: Int): Array<Libro?> {
            return arrayOfNulls(size)
        }
    }

}