package com.akash.sischatapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.ActivityRegisterFinalPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import showTopSideToast

class RegisterFinalPage : ComponentActivity() {
    var binding: ActivityRegisterFinalPageBinding? = null
    var auth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var storage: FirebaseStorage? = null
    var selectedImage: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterFinalPageBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()

        binding!!.back.setOnClickListener { onBackPressed() }
        binding!!.uploadBtn.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 45)
        }
        binding!!.continueBtn.setOnClickListener {
            if(intent.extras!!.isEmpty)
                showTopSideToast(this@RegisterFinalPage, "Please upload your image", "short")
            else {
                startActivity(Intent(this, ConfirmRegistation::class.java))
            }
        }
    }
}