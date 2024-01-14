package es.bujutsu.vista

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.bujutsu.R
import es.bujutsu.modelo.AdaptadorRecyclerViewSeleccionarTecnicas
import es.bujutsu.modelo.BaseDeDatos
import es.bujutsu.modelo.Waza

class PantallaCrearWaza : AppCompatActivity() {

    private var waza: Waza = Waza(0, "","", arrayListOf())
    private lateinit var recyclerViewSeleccionarTecnicas: RecyclerView
    private lateinit var buttonGuardar:Button
    private lateinit var buttonBuscar: Button
    private lateinit var editTextNombre: EditText
    private lateinit var editTextPosicion: EditText
    private lateinit var editTextBrazo: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_crear_waza)

        inicializarComponentes()

        cargarRecyclerView()

        pedirNombreYDescripcion()

        buttonBuscar.setOnClickListener {

            buscarTecnicas()

        }

        buttonGuardar.setOnClickListener {

            guardarWaza()

        }

    }

    private fun inicializarComponentes() {

        buttonGuardar = findViewById(R.id.buttonGuardarWaza)
        buttonBuscar = findViewById(R.id.buttonBuscar)
        editTextNombre = findViewById(R.id.editTextBuscarNombre)
        editTextPosicion = findViewById(R.id.editTextBuscarPosicion)
        editTextBrazo = findViewById(R.id.editTextBuscarBrazo)
        recyclerViewSeleccionarTecnicas = findViewById(R.id.recyclerViewSeleccionarTecnicas)
        recyclerViewSeleccionarTecnicas.layoutManager = LinearLayoutManager(this)

    }

    // Este método carga las todas las técnicas que hay en la base de datos
    private fun cargarRecyclerView(nombre: String? = null, posicion: String? = null, brazo: String? = null) {

        val baseDeDatos: BaseDeDatos = BaseDeDatos(this, "BujutsuSosei", null, 1)

        var adaptadorTecnicas = AdaptadorRecyclerViewSeleccionarTecnicas(baseDeDatos.buscarTecnicas(nombre, posicion, brazo))

        recyclerViewSeleccionarTecnicas.adapter = adaptadorTecnicas

    }

    // Este método muestra un alert dialog en el que se introducen el nombre y la descripción del waza que se va a guardar
    private fun pedirNombreYDescripcion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ingrese el nombre y la descripción del waza")

        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.pedir_nombre_descripcion_waza, null)
        val inputNombre = dialogView.findViewById<EditText>(R.id.editTextNombre)
        val inputDescripcion = dialogView.findViewById<EditText>(R.id.editTextDescripcion)
        val botonAceptar = dialogView.findViewById<Button>(R.id.botonAceptar)

        builder.setView(dialogView)

        val dialog = builder.create()

        botonAceptar.setOnClickListener {

            waza.nombre = inputNombre.text.toString()

            waza.descripcion = inputDescripcion.text.toString()

            dialog.dismiss()

        }

        builder.setCancelable(false)

        dialog.setCanceledOnTouchOutside(false)

        dialog.window?.setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        dialog.show()

    }

    // Este método crea un alert dialog
    private fun mostrarAlertDialog(titulo: String, mensaje: String) {

        val builder = AlertDialog.Builder(this)

        builder.setTitle(titulo)

            .setMessage(mensaje)

            .setPositiveButton("Aceptar") { _, _ -> }

        val dialog = builder.create()

        dialog.show()

    }

    // Este método busca técnicas en la base de datos
    private fun buscarTecnicas() {

        var nombre: String = editTextNombre.text.toString()
        var posicion: String = editTextPosicion.text.toString()
        var brazo: String = editTextBrazo.text.toString()

        cargarRecyclerView(nombre, posicion, brazo)

    }

    // Este método guarda el waza en la base de datos si contiene al menos una técnica, si no muestra un alert dialog con un mensaje de error
    private fun guardarWaza() {

        val baseDeDatos: BaseDeDatos = BaseDeDatos(this, "BujutsuSosei", null, 1)

        val tecnicasSeleccionadas = (recyclerViewSeleccionarTecnicas.adapter as AdaptadorRecyclerViewSeleccionarTecnicas).getTecnicasSeleccionadas()

        if (tecnicasSeleccionadas.isEmpty()) {

            mostrarAlertDialog("Error", "Debe seleccionar al menos una técnica antes de guardar.")

        } else {

            waza.tecnicas = tecnicasSeleccionadas

            baseDeDatos.crearWaza(waza)

            val PantallaWazas = Intent(this, PantallaSeleccionar::class.java)

            PantallaWazas.putExtra("MOSTRAR_BOTON_AGREGAR", true)

            startActivity(PantallaWazas)

            this.finish()

        }

    }

    fun getContext(): Context{

        return this

    }

    override fun onBackPressed() {

        val PantallaWazas = Intent(this, PantallaSeleccionar::class.java)

        PantallaWazas.putExtra("MOSTRAR_BOTON_AGREGAR", true)

        startActivity(PantallaWazas)

        this.finish()

    }

}