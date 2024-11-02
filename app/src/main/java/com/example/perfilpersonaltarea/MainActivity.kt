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

        val spinner_paises = findViewById<Spinner>(R.id.spinner)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.opciones_paises, R.layout.spinner_paises
        )

        adapter.setDropDownViewResource(R.layout.spinner_paises)

        spinner_paises.adapter = adapter


    }
}