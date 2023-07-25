package com.akash.sischatapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.akash.sischatapp.R
import com.akash.sischatapp.util.SharedPref
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class WelcomeScreen : AppCompatActivity() {
    private lateinit var _join_now_btn: Button
    private val sharedPref: SharedPref = SharedPref()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        _join_now_btn = findViewById(R.id.join_now)
        Firebase.initialize(context = this)
        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance(),
        )


        if(sharedPref!!.getString(applicationContext,"is_registered") == "true") {
            startActivity(Intent(this@WelcomeScreen, AppBottomNav::class.java))
            finish()
        }

        _join_now_btn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }
}