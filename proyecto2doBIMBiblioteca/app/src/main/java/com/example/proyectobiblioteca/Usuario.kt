package com.example.proyectobiblioteca

import android.os.Parcel
import android.os.Parcelable

class Usuario(
    var idUsuario: String,
    var nombreUsuario: String,
    var apellidoUsuario: String,
    var fechaNacimientoUsuario: String,
    var direccionUsuario: String,
    var telefonoUsuario: String,
    var emailUsuario: String,

): Parcelable {
    constructor(parcel: Parcel): this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ){

    }

    override fun toString(): String {
        return super.toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idUsuario)
        parcel.writeString(nombreUsuario)
        parcel.writeString(apellidoUsuario)
        parcel.writeString(fechaNacimientoUsuario)
        parcel.writeString(direccionUsuario)
        parcel.writeString(telefonoUsuario)
        parcel.writeString(emailUsuario)
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
