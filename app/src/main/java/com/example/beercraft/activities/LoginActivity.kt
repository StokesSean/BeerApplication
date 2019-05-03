package com.example.beercraft.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.beercraft.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.loginactivity.*
import kotlinx.android.synthetic.main.loginactivity.btnlogin
import kotlinx.android.synthetic.main.loginactivity.email
import kotlinx.android.synthetic.main.loginactivity.password
import kotlinx.android.synthetic.main.loginorsignupview.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginactivity)
        btnlogin.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            info("Login attempt with email : $email and pass word $password")
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if (!it.isSuccessful) return@addOnCompleteListener
                    if(it.isSuccessful){
                        val intent = Intent(this, AlcoholicListActivity::class.java)
                        startActivity(intent)

                    } else {
                        return@addOnCompleteListener
                    }
                }
                .addOnFailureListener{
                    toast("Failed to sign in user : ${it.message}")
                }
        }
    }
}