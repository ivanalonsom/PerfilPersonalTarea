package com.example.perfilpersonaltarea
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

private lateinit var nombreEditText: EditText
private lateinit var apellidoEditText: EditText
private lateinit var correoEditText: EditText
private lateinit var generoRadioGroup: RadioGroup
private lateinit var paisSpinner: Spinner
private lateinit var lecturaCheckBox: CheckBox
private lateinit var deporteCheckBox: CheckBox
private lateinit var musicaCheckBox: CheckBox
private lateinit var arteCheckBox: CheckBox
private lateinit var nivelSatisfaccionSeekBar: SeekBar
private lateinit var nivelSatisfaccionTextView: TextView
private lateinit var suscripcionSwitch: SwitchCompat
private lateinit var guardarBoton: Button
private lateinit var resumenTextView: TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        nombreEditText = findViewById(R.id.editTextNombre)
        apellidoEditText = findViewById(R.id.editTextApellido)
        correoEditText = findViewById(R.id.editTextCorreo)
        generoRadioGroup = findViewById(R.id.radioGroupGenero)
        paisSpinner = findViewById(R.id.spinnerPais)
        lecturaCheckBox = findViewById(R.id.checkButtonLectura)
        deporteCheckBox = findViewById(R.id.checkButtonDeporte)
        musicaCheckBox = findViewById(R.id.checkButtonMusica)
        arteCheckBox = findViewById(R.id.checkButtonArte)
        nivelSatisfaccionSeekBar = findViewById(R.id.seekBarSatisfaccion)
        nivelSatisfaccionTextView = findViewById(R.id.textViewSatisfaccion)
        suscripcionSwitch = findViewById(R.id.switchSuscripcion)
        guardarBoton = findViewById(R.id.buttonGuardar)
        resumenTextView = findViewById(R.id.textViewResumen)

        //getResumenPerfil(nombreEditText, apellidoEditText, correoEditText, )
        val spinnerPaises = findViewById<Spinner>(R.id.spinnerPais)

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

        spinnerPaises.adapter = adaptador

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
            val hobbies = getHobbiesSeleccionados()






        }

    }


    private fun getHobbiesSeleccionados() : List<String> {

        val hobbies = mutableListOf<String>()
        //if(lecturaCheckBox.isChecked)
        when{
            lecturaCheckBox.isChecked -> hobbies.add(R.string.lectura.toString())
            deporteCheckBox.isChecked -> hobbies.add(R.string.deporte.toString())
            musicaCheckBox.isChecked -> hobbies.add(R.string.musica.toString())
            arteCheckBox.isChecked -> hobbies.add(R.string.arte.toString())

        }
        return hobbies
    }


    private fun getResumenPerfil(nom : String, ap : String, email : String, genero : String,
                                 pais : String, hobbies : List<String>, satisf : String, susc : String){

        resumenTextView.setText(getString(R.string.resumen_general, nom, ap, email, genero, pais,
            hobbies.joinToString(", "), satisf, susc) )

    }







}