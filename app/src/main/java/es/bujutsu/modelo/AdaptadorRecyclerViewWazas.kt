package es.bujutsu.modelo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import es.bujutsu.R
import es.bujutsu.vista.PantallaVisualizar

class AdaptadorRecyclerViewWazas(private var wazas: MutableList<Waza>, private val context: Context) : RecyclerView.Adapter<AdaptadorRecyclerViewWazas.ViewHolderWazas>() {

    class ViewHolderWazas(val button: Button) : RecyclerView.ViewHolder(button)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderWazas {

        val button = LayoutInflater.from(parent.context).inflate(R.layout.button_item, parent, false) as Button

        return ViewHolderWazas(button)

    }

    override fun onBindViewHolder(holder: ViewHolderWazas, position: Int) {

        var waza = wazas[position]

        holder.button.text = wazas[position].nombre

        holder.button.setOnClickListener {

            val pantallaVisualizarWaza = Intent(context, PantallaVisualizar::class.java)

            pantallaVisualizarWaza.putExtra("wazaSeleccionado", waza)

            pantallaVisualizarWaza.putExtra("MOSTRAR_BOTON_VIDEO", false)

            context.startActivity(pantallaVisualizarWaza)

        }

    }

    override fun getItemCount() = wazas.size

    // Este método borra un waza del arraylist de wazas
    fun borrarWaza(position: Int) {

        wazas.removeAt(position)

    }

    fun getWaza(position: Int): Waza {

        return wazas[position]

    }

}
