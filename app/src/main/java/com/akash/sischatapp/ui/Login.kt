package com.akash.sischatapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.akash.sischatapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import showTopSideToast


class Login : ComponentActivity() {
    var binding: ActivityLoginBinding? = null
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        auth = FirebaseAuth.getInstance()
        if(auth!!.currentUser != null) {
            val intent = Intent(this@Login, ConfirmRegistation::class.java)
            startActivity(intent)
            finish()
        }
        binding!!.phoneNumberEt.requestFocus()
        binding!!.continueBtn.setOnClickListener {
            if (binding!!.phoneNumberEt.text.length == 11 && binding!!.phoneNumberEt.text.startsWith("01")) {
                val intent = Intent(this@Login, OtpVerify::class.java)
                intent.putExtra("phone", binding!!.phoneNumberEt.text.toString())
                startActivity(intent)
            } else {
                showTopSideToast(this, "Enter a valid phone number", "short")
            }
        }
    }
}