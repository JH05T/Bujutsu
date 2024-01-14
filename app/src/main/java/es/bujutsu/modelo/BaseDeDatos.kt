package es.bujutsu.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream

class BaseDeDatos(
    context: Context,
    name: String = "BujutsuSosei",
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    private val dbPath = context.getDatabasePath(name).path

    private val assetDBPath = name + ".db"

    // Este método crea una copia de la base de datos de assets si no existe
    init {

        if (!comprobarBaseDeDatos()) {

            copiarBaseDeDatosDeAssets(context)

        }

    }

    // Este método comprueba si la base de datos existe
    private fun comprobarBaseDeDatos(): Boolean {

        return File(dbPath).exists()

    }

    // Este método copia la base de datos que hay en la carpeta assets
    private fun copiarBaseDeDatosDeAssets(context: Context) {

        context.assets.open(assetDBPath).use { input ->

            FileOutputStream(dbPath).use { output ->

                input.copyTo(output)

            }

        }

        SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE).close()

    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    // Esté método te devuelve todas las técnicas que pertenecen a un kata a partir del código del kata
    fun obtenerTecnicasDelKata(codigoKata: Int): ArrayList<Tecnica> {

        val tecnicas = ArrayList<Tecnica>()

        val db: SQLiteDatabase = this.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT t.Codigo_Tecnica, t.Nombre, t.Brazo, t.Posicion, t.Direccion, t.Imagen, t.Descripcion FROM Tecnicas t INNER JOIN Tecnicas_Katas tk ON t.Codigo_Tecnica = tk.Codigo_Tecnica WHERE tk.Codigo_Kata = ? ORDER BY tk.Orden", arrayOf(codigoKata.toString()))

        var codigoTecnica: Int
        var nombre: String
        var brazo: String
        var posicion: String
        var direccion: Int
        var imagen: String
        var descripcion: String
        var tecnica: Tecnica

        if (cursor.moveToFirst()) {

            do {

                codigoTecnica = cursor.getInt(cursor.getColumnIndexOrThrow("Codigo_Tecnica"))
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"))
                brazo = cursor.getString(cursor.getColumnIndexOrThrow("Brazo"))
                posicion = cursor.getString(cursor.getColumnIndexOrThrow("Posicion"))
                direccion = cursor.getInt(cursor.getColumnIndexOrThrow("Direccion"))
                imagen = cursor.getString(cursor.getColumnIndexOrThrow("Imagen"))
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"))

                tecnica = Tecnica(codigoTecnica, nombre, brazo, posicion, direccion, imagen, descripcion)

                tecnicas.add(tecnica)

            } while (cursor.moveToNext())

        }

        cursor.close()

        db.close()

        return tecnicas

    }

    // Este método devuelve todos los katas que hay en la base de datos
    fun obtenerKatas(): ArrayList<Kata> {

        val katas = ArrayList<Kata>()

        val db: SQLiteDatabase = this.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT Codigo_Kata, Nombre, Video, Descripcion FROM Katas", null)

        var codigoKata: Int
        var nombre: String
        var video: String
        var descripcion: String
        var kata: Kata

        if (cursor.moveToFirst()) {

            do {

                codigoKata = cursor.getInt(cursor.getColumnIndexOrThrow("Codigo_Kata"))
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"))
                video = cursor.getString(cursor.getColumnIndexOrThrow("Video"))
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"))

                kata = Kata(codigoKata, nombre, video, descripcion, obtenerTecnicasDelKata(codigoKata))

                katas.add(kata)

            } while (cursor.moveToNext())

        }

        cursor.close()

        db.close()

        return katas

    }

    // Esté método te devuelve todas las técnicas que pertenecen a un waza a partir del código del waza
    fun obtenerTecnicasDelWaza(codigoWaza: Int): ArrayList<Tecnica> {

        val tecnicas = ArrayList<Tecnica>()

        val db: SQLiteDatabase = this.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT t.Codigo_Tecnica, t.Nombre, t.Brazo, t.Posicion, t.Direccion, t.Imagen, t.Descripcion FROM Tecnicas t INNER JOIN Waza_Tecnicas wt ON t.Codigo_Tecnica = wt.Codigo_Tecnica WHERE wt.Codigo_Waza = ? ORDER BY wt.Orden", arrayOf(codigoWaza.toString()))

        var codigoTecnica: Int
        var nombre: String
        var brazo: String
        var posicion: String
        var direccion: Int
        var imagen: String
        var descripcion: String
        var tecnica: Tecnica

        if (cursor.moveToFirst()) {

            do {

                codigoTecnica = cursor.getInt(cursor.getColumnIndexOrThrow("Codigo_Tecnica"))
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"))
                brazo = cursor.getString(cursor.getColumnIndexOrThrow("Brazo"))
                posicion = cursor.getString(cursor.getColumnIndexOrThrow("Posicion"))
                direccion = cursor.getInt(cursor.getColumnIndexOrThrow("Direccion"))
                imagen = cursor.getString(cursor.getColumnIndexOrThrow("Imagen"))
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"))

                tecnica = Tecnica(codigoTecnica, nombre, brazo, posicion, direccion, imagen, descripcion)

                tecnicas.add(tecnica)

            } while (cursor.moveToNext())

        }

        cursor.close()

        db.close()

        return tecnicas

    }

    // Este método devuelve todos los wazas que hay en la base de datos
    fun obtenerWazas(): ArrayList<Waza> {

        val wazas = ArrayList<Waza>()

        val db: SQLiteDatabase = this.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT Codigo_Waza, Nombre, Descripcion FROM Waza", null)

        var codigoWaza: Int

        var nombre: String

        var descripcion: String

        var waza: Waza

        if (cursor.moveToFirst()) {

            do {

                codigoWaza = cursor.getInt(cursor.getColumnIndexOrThrow("Codigo_Waza"))

                nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"))

                descripcion = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"))

                waza = Waza(codigoWaza, nombre, descripcion, obtenerTecnicasDelWaza(codigoWaza))

                wazas.add(waza)

            } while (cursor.moveToNext())

        }

        cursor.close()

        db.close()

        return wazas

    }

    // Este método permite insertar un nuevo waza en la base de datos
    fun crearWaza(waza: Waza) {

        val db: SQLiteDatabase = this.writableDatabase

        val valuesWaza = ContentValues()

        valuesWaza.put("Nombre", waza.nombre)
        valuesWaza.put("Descripcion", waza.descripcion)

        val wazaId = db.insert("Waza", null, valuesWaza)

        for ((orden, tecnica) in waza.tecnicas.withIndex()) {

            val valuesTecnica = ContentValues()

            valuesTecnica.put("Codigo_Waza", wazaId)

            valuesTecnica.put("Codigo_Tecnica", tecnica.codigo)

            valuesTecnica.put("Orden", orden)

            db.insert("Waza_Tecnicas", null, valuesTecnica)
        }

        db.close()

    }

    // Este método permite eliminar un waza de la base de datos
    fun borrarWaza(waza: Waza) {

        val db: SQLiteDatabase = this.writableDatabase

        db.beginTransaction()

        try {

            db.delete("Waza_Tecnicas", "Codigo_Waza = ?", arrayOf(waza.codigo.toString()))

            db.delete("Waza", "Codigo_Waza = ?", arrayOf(waza.codigo.toString()))

            db.setTransactionSuccessful()

        } catch (e: Exception) {

        } finally {

            db.endTransaction()

            db.close()

        }

    }

    // Este método busca técnicas en función del nombre, la posición y/o el brazo
    fun buscarTecnicas(nombre: String? = null, posicion: String? = null, brazo: String? = null): ArrayList<Tecnica> {

        val tecnicas = ArrayList<Tecnica>()

        val db: SQLiteDatabase = this.readableDatabase

        val cursor: Cursor

        val camposSeleccionados: StringBuilder = StringBuilder()

        val contenidoCamposSeleccionados: ArrayList<String> = ArrayList()

        var codigoTecnica: Int
        var nombreTecnica: String
        var brazoTecnica: String
        var posicionTecnica: String
        var direccionTecnica: Int
        var imagenTecnica: String
        var descripcionTecnica: String
        var tecnica: Tecnica

        if (!nombre.isNullOrBlank()) {

            camposSeleccionados.append("Nombre LIKE ? COLLATE NOCASE ")

            contenidoCamposSeleccionados.add("%$nombre%")

        }

        if (!posicion.isNullOrBlank()) {

            if (camposSeleccionados.isNotEmpty()){

                camposSeleccionados.append("AND ")

            }

            camposSeleccionados.append("Posicion LIKE ? COLLATE NOCASE ")

            contenidoCamposSeleccionados.add("%$posicion%")

        }

        if (!brazo.isNullOrBlank()) {

            if (camposSeleccionados.isNotEmpty()){

                camposSeleccionados.append("AND ")

            }

            camposSeleccionados.append("Brazo LIKE ? COLLATE NOCASE ")

            contenidoCamposSeleccionados.add("%$brazo%")

        }

        cursor = db.query(
            "Tecnicas",
            arrayOf("Codigo_Tecnica", "Nombre", "Brazo", "Posicion", "Direccion", "Imagen", "Descripcion"),
            camposSeleccionados.toString(),
            contenidoCamposSeleccionados.toTypedArray(),
            null,
            null,
            "Nombre, Posicion, Brazo, Direccion"
        )

        if (cursor.moveToFirst()) {

            do {

                codigoTecnica = cursor.getInt(cursor.getColumnIndexOrThrow("Codigo_Tecnica"))
                nombreTecnica = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"))
                brazoTecnica = cursor.getString(cursor.getColumnIndexOrThrow("Brazo"))
                posicionTecnica = cursor.getString(cursor.getColumnIndexOrThrow("Posicion"))
                direccionTecnica = cursor.getInt(cursor.getColumnIndexOrThrow("Direccion"))
                imagenTecnica = cursor.getString(cursor.getColumnIndexOrThrow("Imagen"))
                descripcionTecnica = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"))

                tecnica = Tecnica(codigoTecnica, nombreTecnica, brazoTecnica, posicionTecnica, direccionTecnica, imagenTecnica, descripcionTecnica)

                tecnicas.add(tecnica)

            } while (cursor.moveToNext())

        }

        cursor.close()
        db.close()

        return tecnicas

    }

}
