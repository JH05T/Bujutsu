package es.bujutsu.vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.bujutsu.R
import es.bujutsu.modelo.AdaptadorRecyclerViewKatas
import es.bujutsu.modelo.AdaptadorRecyclerViewWazas
import es.bujutsu.modelo.BaseDeDatos

class PantallaSeleccionar: AppCompatActivity() {

    private var mostrarBotonAgregar: Boolean = false
    private lateinit var buttonAgregar: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptadorRecyclerViewWazas: AdaptadorRecyclerViewWazas
    private lateinit var adaptadorRecyclerViewKatas: AdaptadorRecyclerViewKatas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_seleccionar)

        mostrarBotonAgregar = intent.getBooleanExtra("MOSTRAR_BOTON_AGREGAR", false)

        inicializarComponentes()

        crearBotonAgregar()

        buttonAgregar.setOnClickListener(){

            irACrearWaza()

        }

    }

    private fun inicializarComponentes() {

        buttonAgregar = findViewById(R.id.buttonCrearWaza)
        recyclerView = findViewById(R.id.recyclerViewSeleccionar)

    }

    // Este método comprueba se lo que se van a mostrar son katas o wazas para mostrarlos y en caso de ser un waza muestra el botón crear un nuevo waza
    private fun crearBotonAgregar() {

        if (mostrarBotonAgregar) {

            buttonAgregar.visibility = View.VISIBLE

            mostrarWazas()

        } else {

            buttonAgregar.visibility = View.GONE

            mostrarKatas()

        }

    }

    // Este método muestra los wazas que hay en la base de datos
    private fun mostrarWazas() {

        var baseDeDatos: BaseDeDatos = BaseDeDatos(this, "BujutsuSosei", null, 1)

        val wazas = baseDeDatos.obtenerWazas()

        recyclerView.layoutManager = LinearLayoutManager(this)

        adaptadorRecyclerViewWazas = AdaptadorRecyclerViewWazas(wazas, this)

        recyclerView.adapter = adaptadorRecyclerViewWazas

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {

                return false

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                val position = viewHolder.adapterPosition

                if (swipeDir == ItemTouchHelper.LEFT || swipeDir == ItemTouchHelper.RIGHT) {

                    val waza = adaptadorRecyclerViewWazas.getWaza(position)

                    baseDeDatos.borrarWaza(waza)

                    adaptadorRecyclerViewWazas.borrarWaza(position)

                    adaptadorRecyclerViewWazas.notifyDataSetChanged()

                }

            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)

        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    // Este método muestra los katas que hay en la base de datos
    private fun mostrarKatas() {

        var baseDeDatos: BaseDeDatos = BaseDeDatos(this, "BujutsuSosei", null, 1)

        val katas = baseDeDatos.obtenerKatas()

        recyclerView.layoutManager = LinearLayoutManager(this)

        adaptadorRecyclerViewKatas = AdaptadorRecyclerViewKatas(katas, this)

        recyclerView.adapter = adaptadorRecyclerViewKatas

    }

    // Este método te lleva a la pantalla para crear wazas
    private fun irACrearWaza() {

        val PantallaCrearWaza = Intent(this, PantallaCrearWaza::class.java)

        startActivity(PantallaCrearWaza)

        this.finish()

    }

    override fun onBackPressed() {

        val PantallaInicio = Intent(this, PantallaInicio::class.java)

        startActivity(PantallaInicio)

        this.finish()

    }

}
