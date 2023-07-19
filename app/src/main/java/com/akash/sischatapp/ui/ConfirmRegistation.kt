package com.akash.sischatapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.akash.sischatapp.R

class ConfirmRegistation : ComponentActivity() {
    private lateinit var _register: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_registation)

        _register = findViewById(R.id.continue_btn)

        _register.setOnClickListener {
            startActivity(Intent(this, WelcomeScreen::class.java))
        }
    }
}