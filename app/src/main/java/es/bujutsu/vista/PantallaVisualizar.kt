package es.bujutsu.vista

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.bujutsu.R
import es.bujutsu.modelo.AdaptadorRecyclerViewVerTecnicas
import es.bujutsu.modelo.Kata
import es.bujutsu.modelo.Waza

class PantallaVisualizar : AppCompatActivity() {

    private var mostrarBotonVideo: Boolean = false
    private lateinit var recyclerViewTecnicas: RecyclerView
    private lateinit var buttonVideo: Button
    private lateinit var adaptadorTecnicas: AdaptadorRecyclerViewVerTecnicas
    private lateinit var containerVideo: FrameLayout
    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_visualizar)

        mostrarBotonVideo = intent.getBooleanExtra("MOSTRAR_BOTON_VIDEO", false)

        inicializarComponentes()

        crearBotonVideo()

        buttonVideo.setOnClickListener {

            verOCerrarVideo()

        }

    }

    private fun inicializarComponentes() {

        buttonVideo = findViewById(R.id.buttonVerVideo)
        containerVideo = findViewById(R.id.containerVideo)
        videoView = findViewById(R.id.videoView)

        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)

    }

    // Este método comprueba se lo que se va a visualizar es un kata o un waza para mostrarlo y en caso de ser un kata muestra el botón para ver el vídeo del kata
    private fun crearBotonVideo() {

        containerVideo.visibility = View.GONE

        if (mostrarBotonVideo) {

            buttonVideo.visibility = View.VISIBLE

            mostrarTecnicasKata()

        } else {

            buttonVideo.visibility = View.GONE

            mostrarTecnicasWaza()

        }

    }

    // Este método abre el vídeo en caso de que no se esté visualizando, lo empieza a reproducir desde el principio y cambia el texto del botón a "Cerrar Vídeo", en caso de que se esté visualizando el vídeo se cierra y se cambia el texto del botón a "Ver Vídeo"
    private fun verOCerrarVideo() {

        containerVideo.visibility = if (containerVideo.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        recyclerViewTecnicas.visibility = if (containerVideo.visibility == View.VISIBLE) View.GONE else View.VISIBLE

        if (containerVideo.visibility == View.VISIBLE) {

            buttonVideo.text = "Cerrar Vídeo"

            val kata: Kata? = intent.getParcelableExtra("kataSeleccionado")

            if (kata != null) {

                val uri = Uri.parse("android.resource://$packageName/${resources.getIdentifier(kata.video, "raw", packageName)}")

                videoView.setVideoURI(uri)
                videoView.start()

            }

        } else {

            buttonVideo.text = "Ver Vídeo"
        }

    }

    // Este método muestra las técnicas del waza seleccionado
    private fun mostrarTecnicasWaza() {

        val waza: Waza? = intent.getParcelableExtra("wazaSeleccionado")

        recyclerViewTecnicas = findViewById(R.id.recyclerViewVisualizar)

        recyclerViewTecnicas.layoutManager = LinearLayoutManager(this)

        if (waza != null) {

            adaptadorTecnicas = AdaptadorRecyclerViewVerTecnicas(waza.tecnicas)

            recyclerViewTecnicas.adapter = adaptadorTecnicas

        }

    }

    // Este método muestra las técnicas del kata seleccionado
    private fun mostrarTecnicasKata() {

        val kata: Kata? = intent.getParcelableExtra("kataSeleccionado")

        recyclerViewTecnicas = findViewById(R.id.recyclerViewVisualizar)

        recyclerViewTecnicas.layoutManager = LinearLayoutManager(this)

        if (kata != null) {

            adaptadorTecnicas = AdaptadorRecyclerViewVerTecnicas(kata.tecnicas)

            recyclerViewTecnicas.adapter = adaptadorTecnicas

        }

    }

}
