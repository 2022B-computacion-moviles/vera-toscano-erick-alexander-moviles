package com.example.proyectobiblioteca

import android.os.Parcel
import android.os.Parcelable

class administrador(
    var idUsuario: String,
    var nombre: String,
    var email: String,
    var password: String
): Parcelable{
    constructor(parcel:Parcel): this(
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
        parcel.writeString(idUsuario)
        parcel.writeString(nombre)
        parcel.writeString(email)
        parcel.writeString(password)
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
