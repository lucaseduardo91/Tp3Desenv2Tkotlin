package com.infnetkot.tp3desenv2tkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.infnetkot.tp3desenv2tkotlin.dialog.LoadingAlerta
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth;
    private lateinit var loading : LoadingAlerta
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        callbackManager = CallbackManager.Factory.create()

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

        login_button_facebook.setReadPermissions("email", "public_profile")
        login_button_facebook.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(applicationContext,"Problema na autenticação com o facebook!",Toast.LENGTH_SHORT)
            }
        })

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)

    }

    private fun handleFacebookAccessToken(token: AccessToken) {

        var dialogoFacebook = LoadingAlerta(this)

        dialogoFacebook.startLoadingDialog("Autenticando pelo facebook...")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    dialogoFacebook.dismiss()
                    var intent = Intent(this,MenuActivity::class.java)
                    startActivity(intent)
                } else {
                    dialogoFacebook.dismiss()
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}

