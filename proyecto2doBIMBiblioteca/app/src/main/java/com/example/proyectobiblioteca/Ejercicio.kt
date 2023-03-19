package com.example.proyectobiblioteca

import android.os.Parcel
import android.os.Parcelable

class Ejercicio(
    var idEjercicioReg: String,
    var nombre: String,
    var numeroSeries: String,
    var numeroRepeticiones: String,
    var peso: String,
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
        parcel.writeString(idEjercicioReg)
        parcel.writeString(nombre)
        parcel.writeString(numeroSeries)
        parcel.writeString(numeroRepeticiones)
        parcel.writeString(peso)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ejercicio> {
        override fun createFromParcel(parcel: Parcel): Ejercicio {
            return Ejercicio(parcel)
        }

        override fun newArray(size: Int): Array<Ejercicio?> {
            return arrayOfNulls(size)
        }
    }

}