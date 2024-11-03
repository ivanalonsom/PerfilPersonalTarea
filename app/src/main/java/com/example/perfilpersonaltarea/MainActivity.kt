package com.example.perfilpersonaltarea
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

private lateinit var generoRadioGroup : RadioGroup
private lateinit var lecturaCheckBox : CheckBox
private lateinit var deporteCheckBox : CheckBox
private lateinit var arteCheckBox : CheckBox
private lateinit var musicaCheckBox : CheckBox
private lateinit var resumenTextView : TextView
private lateinit var suscripcionSwitch : SwitchCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val nombreEditText: EditText = findViewById(R.id.editTextNombre)
        val apellidoEditText : EditText= findViewById(R.id.editTextApellido)
        val correoEditText : EditText= findViewById(R.id.editTextCorreo)
        generoRadioGroup = findViewById(R.id.radioGroupGenero)
        val paisesSpinner : Spinner= findViewById<Spinner>(R.id.spinnerPais)
        lecturaCheckBox = findViewById(R.id.checkButtonLectura)
        deporteCheckBox = findViewById(R.id.checkButtonDeporte)
        musicaCheckBox = findViewById(R.id.checkButtonMusica)
        arteCheckBox = findViewById(R.id.checkButtonArte)
        val nivelSatisfaccionSeekBar : SeekBar = findViewById(R.id.seekBarSatisfaccion)
        val nivelSatisfaccionTextView : TextView= findViewById(R.id.textViewSatisfaccion)
        suscripcionSwitch = findViewById(R.id.switchSuscripcion)
        val guardarBoton : Button = findViewById(R.id.buttonGuardar)
        resumenTextView = findViewById(R.id.textViewResumen)


        val adaptador = ArrayAdapter.createFromResource(
            this,
            R.array.opciones_paises, R.layout.tamanoletra_spinner
        )
        // El layout que pasamos aquí controla el estilo de letra del elemento seleccionado
        /* Como alt al adaptador que he creado podría haber hecho:
        val paises = arrayOf("España", "Estados Unidos", "México", "Argentina", "Colombia")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, paises)
         */

        adaptador.setDropDownViewResource(R.layout.tamanoletra_spinner)
        // Este controla el estilo de letra cuando despliego el spinner, en este caso lo mismo.

        paisesSpinner.adapter = adaptador


        nivelSatisfaccionSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                nivelSatisfaccionTextView.setText(getString(R.string.satisfaccion_dinamica, progress))
                /* Podría haber concatenado
                    nivelSatisfaccionTextView.setText(getString(R.string.satisfaccion_dinamica) + progress)
                    pero es mucho mejor en el xml poner el %d y aquí pasar el argumento dentro de
                    getString como he hecho.
                 */
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            /* Estos dos métodos debo tenerlos aunque estén vacíos porque cuando sobreescribo un
             método, estoy obligado a implementar todos
            */
        })


        guardarBoton.setOnClickListener{

            val nombre : String = nombreEditText.text.toString()
            val apellido : String = apellidoEditText.text.toString()
            val email : String = correoEditText.text.toString()
            val genero = getGeneroSeleccionado()
            val hobbies : String = getHobbiesSeleccionados()
            val pais = paisesSpinner.selectedItem.toString()
            val satisfaccion = nivelSatisfaccionSeekBar.progress
            val suscrito = getSuscripcion()

            val nombreNoVacio = verificaCampoObligatorio(nombreEditText)
            val apellidoNoVacio = verificaCampoObligatorio(apellidoEditText)
            val emailNoVacio = verificaCampoObligatorio(correoEditText)
            val emailValido = emailValido(email)

            if (nombreNoVacio && apellidoNoVacio && emailNoVacio) {
                if (emailValido) {
                    getResumenPerfil(nombre,apellido,email,genero,pais,hobbies,satisfaccion,suscrito)
                }
            }
        }

    }


    private fun getGeneroSeleccionado(): String {
        val generoSeleccionadoId : Int = generoRadioGroup.checkedRadioButtonId

        return when (generoSeleccionadoId) {
            R.id.radioButtonMasculino -> getString(R.string.masculino )
            R.id.radioButtonFemenino -> getString(R.string.femenino )
            R.id.radioButtonOtro -> getString(R.string.otro )
            else -> getString(R.string.genero_vacio )
        }
    }


    private fun getHobbiesSeleccionados() : String {

        val hobbies = mutableListOf<String>()

        if (lecturaCheckBox.isChecked) hobbies.add(getString(R.string.lectura))
        if (deporteCheckBox.isChecked) hobbies.add(getString(R.string.deporte))
        if (musicaCheckBox.isChecked) hobbies.add(getString(R.string.musica))
        if (arteCheckBox.isChecked) hobbies.add(getString(R.string.arte))
        // Con un when, al no tener una condición, solo evaluaba lo primero que fuera cierto y salía

        if (hobbies.isNotEmpty()) {
            return hobbies.joinToString(", ")
        }else return getString(R.string.sin_hobbies)
    }


    private fun getSuscripcion() : String{

        return if (suscripcionSwitch.isChecked) getString(R.string.Si) else getString(R.string.No)
    }


    private fun verificaCampoObligatorio(campo: EditText): Boolean {
        val texto = campo.text.toString() // Obtén el texto del campo

        return if (TextUtils.isEmpty(texto)) {
            campo.error = getString(R.string.campo_obligatorio)
            /*
            Muestra un mensaje de error. Más util así para saber si tengo un campo vacío o más
            de uno
             */
            false
        } else {
            true
        }
    }


    private fun emailValido(email : String) : Boolean {

        val esValido : Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        return if (esValido){
            true
        }else{
            //correoEditText.error = getString(R.string.correo_no_valido)
            val toast = Toast.makeText(this, getString(R.string.correo_no_valido), Toast.LENGTH_SHORT)
            toast.show()
            false
        }
    }


    private fun getResumenPerfil(nom : String, ap : String, email : String, genero : String,
                                 pais : String, hobbies : String, satisf : Int, susc : String){


        resumenTextView.setText(getString(R.string.resumen_general, nom, ap, email, genero, pais,
            hobbies, satisf, susc) )

    }







}