package com.akash.sischatapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import com.akash.sischatapp.R

class RegisterFinalPage : ComponentActivity() {
    private lateinit var _back: ImageView
    private lateinit var _register: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_final_page)

        _back = findViewById(R.id.back)
        _back.setOnClickListener { onBackPressed() }
        _register = findViewById(R.id.continue_btn)

        _register.setOnClickListener {
            startActivity(Intent(this, ConfirmRegistation::class.java))
        }
    }
}