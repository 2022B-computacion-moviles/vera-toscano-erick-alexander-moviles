package com.example.proyectobiblioteca

import android.os.Parcel
import android.os.Parcelable

class Prestamo(
    var idPrestamo: String,
    var libro: String,
    var usuario: String,
    var fechaMaximaDevolución: String,
    var devuelto: String,
):Parcelable {
    constructor(parcel: Parcel): this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ){
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idPrestamo)
        parcel.writeString(libro)
        parcel.writeString(usuario)
        parcel.writeString(fechaMaximaDevolución)
        parcel.writeString(devuelto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Prestamo> {
        override fun createFromParcel(parcel: Parcel): Prestamo {
            return Prestamo(parcel)
        }

        override fun newArray(size: Int): Array<Prestamo?> {
            return arrayOfNulls(size)
        }
    }

}