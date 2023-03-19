package com.example.proyectobiblioteca

import android.os.Parcel
import android.os.Parcelable

class autor (
    var idAutor: String,
    var nombreAutor: String,
    var apellidoAutor: String,
    var nacionalidadAutor: String
): Parcelable {
    constructor(parcel: Parcel): this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    ){

    }

    override fun toString(): String {
        return super.toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idAutor)
        parcel.writeString(nombreAutor)
        parcel.writeString(apellidoAutor)
        parcel.writeString(nacionalidadAutor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<administrador> {
        override fun createFromParcel(parcel: Parcel): administrador {
            return administrador(parcel)
        }

        override fun newArray(size: Int): Array<administrador?> {
            return arrayOfNulls(size)
        }
    }
}
