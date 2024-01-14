package es.bujutsu.modelo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.bujutsu.R

class AdaptadorRecyclerViewVerTecnicas(private val tecnicas: List<Tecnica>) :

    RecyclerView.Adapter<AdaptadorRecyclerViewVerTecnicas.ViewHolderTecnicas>() {

    class ViewHolderTecnicas(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageViewTecnica: ImageView = itemView.findViewById(R.id.imageViewTecnica)

        val textViewInfoTecnica: TextView = itemView.findViewById(R.id.textViewInfoTecnica)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTecnicas {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ver_item_tecnica, parent, false)

        return ViewHolderTecnicas(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolderTecnicas, position: Int) {

        cargarImagen(tecnicas[position].imagen, holder.imageViewTecnica)

        holder.textViewInfoTecnica.text = obtenerInformacionTecnica(tecnicas[position])

    }

    // Este método devuelve información de la técnica
    private fun obtenerInformacionTecnica(tecnica: Tecnica): String {

        var msg: String

        if (tecnica.brazo == "" || tecnica.brazo == null){

            msg = tecnica.nombre + "\n\n" + tecnica.posicion

        } else {

            msg = tecnica.nombre + "\n\n" + tecnica.brazo + "\n\n" + tecnica.posicion

        }

        return msg

    }

    // Este método muestra la imagen de la técnica
    private fun cargarImagen(imagen: String, imageViewTecnica: ImageView) {

        val resId = imageViewTecnica.context.resources.getIdentifier(imagen, "drawable", imageViewTecnica.context.packageName)

        imageViewTecnica.setImageResource(resId)

    }

    override fun getItemCount() = tecnicas.size

}
