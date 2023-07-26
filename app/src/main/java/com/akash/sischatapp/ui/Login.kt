package com.akash.sischatapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.akash.sischatapp.databinding.ActivityLoginBinding
import com.akash.sischatapp.util.SharedPref
import com.google.firebase.auth.FirebaseAuth
import showTopToast


class Login : AppCompatActivity() {
    private val sharedPref: SharedPref = SharedPref()
    var binding: ActivityLoginBinding? = null
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        auth = FirebaseAuth.getInstance()
        if(auth!!.currentUser != null && sharedPref!!.getString(applicationContext,"is_registered") == "true") {
            val intent = Intent(this@Login, AppBottomNav::class.java)
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
                showTopToast(this, "Enter a valid phone number", "short", "neutral")
            }
        }
    }
}