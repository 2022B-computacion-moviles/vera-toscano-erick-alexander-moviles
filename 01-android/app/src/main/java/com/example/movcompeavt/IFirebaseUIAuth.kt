package com.example.movcompeavt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth



class IFirebaseUIAuth : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var btnLogout: Button
    lateinit var tvNombre: TextView

    fun cambiarNombre(nombre:String){
        tvNombre.text = nombre
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifirebase_uiauth)
        btnLogin = findViewById(R.id.btn_login_firebase)
        btnLogout = findViewById(R.id.btn_logout_firebase)
        btnLogin.setOnClickListener { enviarIntentLogin() }
        btnLogout.setOnClickListener { logout() }
        tvNombre = findViewById(R.id.tv_nombre_firebase)
        cambiarNombre("Ingrese por favor")
    }

    fun enviarIntentLogin(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            // AuthUI.IdpConfig.FacebookBuilder().build()
        )
        // Create and launch sign-in intent
        val signInIntent = AuthUI
            .getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        respuestaLoginIntent.launch(signInIntent)
    }

    fun logout(){
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE
        cambiarNombre("Ingrese por favor")
        FirebaseAuth.getInstance().signOut()
    }

    private val respuestaLoginIntent = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode === RESULT_OK) {
            if (res.idpResponse != null) {
                seLogeo(res.idpResponse!!)
            }
        }
    }

    fun seLogeo(
        res: IdpResponse
    ) {
        btnLogout.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE
        cambiarNombre(res.email!!)
        if(res.isNewUser == true){
            registrarUsuarioPorPrimeraVez(res)
        }
    }
    fun registrarUsuarioPorPrimeraVez(
        usuario: IdpResponse
    ){
        // LOGICA NEGOCIO
    }
}
