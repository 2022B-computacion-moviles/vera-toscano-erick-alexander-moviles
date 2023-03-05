package com.example.deber02_recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //funcion para ir a la actividad dashboard con el boton pinBtn
        val botonPin =findViewById<Button>(R.id.pinBtn)
        val botonPassword = findViewById<Button>(R.id.passBtn)
        val botonHuella = findViewById<Button>(R.id.fingerBtn)

        botonPin.setOnClickListener{
            irActividad(dashboard::class.java)
        }
        botonPassword.setOnClickListener{
            irActividad(dashboard::class.java)
        }
        botonHuella.setOnClickListener{
            irActividad(dashboard::class.java)
        }

    }

    //funcion para ir a la actividad
    fun irActividad(clase: Class<*>){
        val intent= Intent(this, clase)
        startActivity(intent)
    }

}