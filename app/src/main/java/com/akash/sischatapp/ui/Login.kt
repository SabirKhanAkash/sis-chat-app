package com.akash.sischatapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.akash.sischatapp.R

class Login : ComponentActivity() {
    private lateinit var _phone_number: EditText
    private lateinit var _continue: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        _phone_number = findViewById(R.id.phone_number_et)
        _continue = findViewById(R.id.continue_btn)

        _continue.setOnClickListener {
            startActivity(Intent(this, OtpVerify::class.java))
        }
    }
}