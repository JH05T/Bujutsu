package es.bujutsu.vista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageButton
import es.bujutsu.R

class PantallaInicio : AppCompatActivity() {

    private lateinit var buttonKatas: AppCompatImageButton
    private lateinit var buttonWazas: AppCompatImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_inicio)

        inicializarComponentes()

        buttonKatas.setOnClickListener {

            irAVerKatas()

        }

        buttonWazas.setOnClickListener {

            irAVerWazas()

        }

    }

    private fun inicializarComponentes() {

        buttonKatas = findViewById(R.id.ButtonKata)
        buttonWazas = findViewById(R.id.buttonWaza)

    }

    private fun irAVerKatas() {

        val PantallaKatas = Intent(this, PantallaSeleccionar::class.java)

        PantallaKatas.putExtra("MOSTRAR_BOTON_AGREGAR", false)

        startActivity(PantallaKatas)

        this.finish()

    }

    private fun irAVerWazas() {

        val PantallaWazas = Intent(this, PantallaSeleccionar::class.java)

        PantallaWazas.putExtra("MOSTRAR_BOTON_AGREGAR", true)

        startActivity(PantallaWazas)

        this.finish()

    }

    override fun onBackPressed() {

        this.finish()

    }

}