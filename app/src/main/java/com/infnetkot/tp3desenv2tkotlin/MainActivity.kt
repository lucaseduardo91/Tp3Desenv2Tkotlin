package com.infnetkot.tp3desenv2tkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.infnetkot.tp3desenv2tkotlin.dialog.LoadingAlerta
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth;
    private lateinit var loading : LoadingAlerta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        btn_tela_cadastro.setOnClickListener {
            var intent = Intent(this,CadastroContaActivity::class.java)
            startActivity(intent)
        }

        btn_logar.setOnClickListener {
            var email = emailexistente.text.toString()
            var senha = password_existente.text.toString()

            if(!email.isNullOrBlank() && !senha.isNullOrBlank())
            {
                loading = LoadingAlerta(this)
                loading.startLoadingDialog("Carregando...")
                logarUsuario(email,senha)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null)
        {
            var intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }
    }

    fun logarUsuario(email : String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    loading.dismiss()
                    var intent = Intent(this,MenuActivity::class.java)
                    startActivity(intent)
                } else {
                    loading.dismiss()
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

