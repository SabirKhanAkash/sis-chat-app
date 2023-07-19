package com.akash.sischatapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import com.akash.sischatapp.R

class RegisterPageOne : ComponentActivity() {
    private lateinit var _back: ImageView
    private lateinit var _continue: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page_one)

        _back = findViewById(R.id.back)
        _back.setOnClickListener { onBackPressed() }
        _continue = findViewById(R.id.continue_btn)

        _continue.setOnClickListener {
            startActivity(Intent(this, RegisterFinalPage::class.java))
        }
    }
}