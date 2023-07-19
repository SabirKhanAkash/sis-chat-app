package com.akash.sischatapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.akash.sischatapp.R

class WelcomeScreen : ComponentActivity() {
    private lateinit var _join_now_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        _join_now_btn = findViewById(R.id.join_now)

        _join_now_btn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }
}