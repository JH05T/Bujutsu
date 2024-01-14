package es.bujutsu.modelo

import android.os.Parcel
import android.os.Parcelable

class Tecnica(
    var codigo: Int,
    var nombre: String,
    var brazo: String,
    var posicion: String,
    var direccion: Int,
    var imagen: String,
    var descripcion: String
) : Parcelable {

    constructor(parcel: Parcel) : this(

        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {

        parcel.writeInt(codigo)
        parcel.writeString(nombre)
        parcel.writeString(brazo)
        parcel.writeString(posicion)
        parcel.writeInt(direccion)
        parcel.writeString(imagen)
        parcel.writeString(descripcion)

    }

    override fun describeContents(): Int {

        return 0

    }

    companion object CREATOR : Parcelable.Creator<Tecnica> {

        override fun createFromParcel(parcel: Parcel): Tecnica {

            return Tecnica(parcel)

        }

        override fun newArray(size: Int): Array<Tecnica?> {

            return arrayOfNulls(size)

        }

    }

}
