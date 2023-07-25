package com.akash.sischatapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.ActivityConfirmRegistationBinding

class ConfirmRegistation : ComponentActivity() {
    var binding: ActivityConfirmRegistationBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmRegistationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.continueBtn.setOnClickListener {
            startActivity(Intent(this, AppBottomNav::class.java))
        }
    }
}