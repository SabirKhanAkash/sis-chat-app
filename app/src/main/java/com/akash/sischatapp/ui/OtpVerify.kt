package com.akash.sischatapp.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.ActivityOtpVerifyBinding
import com.google.firebase.auth.FirebaseAuth
import com.mukeshsolanki.OtpView

class OtpVerify : ComponentActivity() {
    var binding: ActivityOtpVerifyBinding? = null
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerifyBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

//        auth = FirebaseAuth.getInstance()
        binding!!.back.setOnClickListener { onBackPressed() }
        binding!!.otpView.requestFocus()
        val phone = intent.getStringExtra("phone")
        binding!!.phone.setText("Check your SMS messages. we've sent the verification code to $phone")
        binding!!.otpView.setOtpCompletionListener {
            if (it.toString() == "4423") {
                binding!!.wrongOtpPrompt.visibility = View.INVISIBLE
                binding!!.otpView.setLineColor(Color.parseColor("#CCD1D7")) //GRAY
            } else {
                binding!!.otpView.setText("")
                binding!!.wrongOtpPrompt.visibility = View.VISIBLE
                binding!!.otpView.setLineColor(Color.parseColor("#FB0000")) //RED
            }
        }
        binding!!.continueBtn.setOnClickListener {
            startActivity(Intent(this, RegisterPageOne::class.java))
        }
    }
}