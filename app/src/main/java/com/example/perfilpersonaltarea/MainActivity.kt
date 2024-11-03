package com.example.perfilpersonaltarea
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val spinnerPaises = findViewById<Spinner>(R.id.spinnerPais)

        val adaptador = ArrayAdapter.createFromResource(
            this,
            R.array.opciones_paises, R.layout.tamanoletra_spinner
        )
        // El layout que pasamos aquí controla el estilo de letra del elemento seleccionado

        adaptador.setDropDownViewResource(R.layout.tamanoletra_spinner)
        // Este controla el estilo de letra cuando despliego el spinner, en este caso lo mismo.

        spinnerPaises.adapter = adaptador

    }


    fun muestraDatos(){





    }










}