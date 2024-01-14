package es.bujutsu.modelo

import android.os.Parcel
import android.os.Parcelable

class Kata(
    var codigo: Int,
    var nombre: String,
    var video: String,
    var descripcion: String,
    var tecnicas: List<Tecnica>
) : Parcelable {

    constructor(parcel: Parcel) : this(

        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        mutableListOf<Tecnica>().apply {

            parcel.readList(this, Tecnica::class.java.classLoader)

        }

    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {

        parcel.writeInt(codigo)
        parcel.writeString(nombre)
        parcel.writeString(video)
        parcel.writeString(descripcion)
        parcel.writeList(tecnicas)

    }

    override fun describeContents(): Int {

        return 0

    }

    companion object CREATOR : Parcelable.Creator<Kata> {

        override fun createFromParcel(parcel: Parcel): Kata {

            return Kata(parcel)

        }

        override fun newArray(size: Int): Array<Kata?> {

            return arrayOfNulls(size)

        }

    }

}
