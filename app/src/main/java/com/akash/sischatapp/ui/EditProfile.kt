package com.akash.sischatapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.ActivityEditProfileBinding

class EditProfile : AppCompatActivity() {
    var binding: ActivityEditProfileBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.back.setOnClickListener { onBackPressed() }
        binding!!.continueBtn.setOnClickListener {
            startActivity(Intent(applicationContext, AppBottomNav::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }
}