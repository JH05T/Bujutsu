package es.bujutsu.modelo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import es.bujutsu.R
import es.bujutsu.vista.PantallaVisualizar

class AdaptadorRecyclerViewKatas(private val katas: List<Kata>, private val context: Context) : RecyclerView.Adapter<AdaptadorRecyclerViewKatas.ViewHolderKatas>() {

        class ViewHolderKatas(val button: Button) : RecyclerView.ViewHolder(button)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderKatas {

            val button = LayoutInflater.from(parent.context).inflate(R.layout.button_item, parent, false) as Button

            return ViewHolderKatas(button)

        }

        override fun onBindViewHolder(holder: ViewHolderKatas, position: Int) {

            val kata = katas[position]

            holder.button.text = katas[position].nombre

            holder.button.setOnClickListener {

                val pantallaVisualizarKata = Intent(context, PantallaVisualizar::class.java)

                pantallaVisualizarKata.putExtra("kataSeleccionado", kata)

                pantallaVisualizarKata.putExtra("MOSTRAR_BOTON_VIDEO", true)

                context.startActivity(pantallaVisualizarKata)

            }

        }

        override fun getItemCount() = katas.size

}