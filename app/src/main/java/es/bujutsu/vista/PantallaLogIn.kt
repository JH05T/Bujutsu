package es.bujutsu.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import es.bujutsu.R

class PantallaLogIn : AppCompatActivity() {

    private lateinit var buttonSignIn: Button
    private lateinit var buttonSignUp: Button
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextPasword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_log_in)

        inicializarComponentes()

        buttonSignIn.setOnClickListener(){

            iniciarSesion()

        }

        buttonSignUp.setOnClickListener(){

            registrarNuevoCorreo()

        }

    }

    private fun inicializarComponentes() {

        buttonSignIn = findViewById(R.id.buttonSignIn)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        editTextCorreo = findViewById(R.id.editTextCorreo)
        editTextPasword = findViewById(R.id.editTextPassword)

    }

    // Este método comprueba que el correo y la contraseña son correctos, en caso contrario muestra un mensaje de error que indica que el correo o la contraseña son incorrectos
    private fun iniciarSesion() {

        if (editTextCorreo.text.isNotEmpty() && editTextPasword.text.isNotEmpty()){

            FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextCorreo.text.toString(), editTextPasword.text.toString()).addOnCompleteListener {

                if (it.isSuccessful){

                    val PantallaInicio = Intent(this, PantallaInicio::class.java)

                    startActivity(PantallaInicio)

                    this.finish()

                } else {

                    mostrarAlertDialog("Error", "El correo o la contraseña son incorrectos.")

                }

            }

        } else {

            mostrarAlertDialog("Error", "Debes introducir un correo y una contraseña.")

        }

    }

    // Este método registra a un nuevo correo y muestra un mensaje de error en el caso de que el correo ya exista
    private fun registrarNuevoCorreo(){

        if (editTextCorreo.text.isNotEmpty() && editTextPasword.text.isNotEmpty()){

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextCorreo.text.toString(), editTextPasword.text.toString()).addOnCompleteListener {

                if (it.isSuccessful){

                    mostrarAlertDialog("", "Correo registrado correctamente")

                } else {

                    mostrarAlertDialog("Error", "Su correo no se registró correctamente.")

                }

            }

        } else {

            mostrarAlertDialog("Error", "Debes introducir un correo y una contraseña.")

        }

    }


    // Este método crea un alert dialog para mostrar un mensaje
    private fun mostrarAlertDialog(titulo: String, mensaje: String) {

        val builder = AlertDialog.Builder(this)

        builder.setTitle(titulo)

            .setMessage(mensaje)

            .setPositiveButton("Aceptar") { _, _ -> }

        val dialog = builder.create()

        dialog.show()

    }

    override fun onBackPressed() {

        this.finish()

    }

}