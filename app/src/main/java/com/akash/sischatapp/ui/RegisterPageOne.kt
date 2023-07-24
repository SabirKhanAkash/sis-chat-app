package com.akash.sischatapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.ActivityRegisterPageOneBinding
import com.akash.sischatapp.util.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class RegisterPageOne : ComponentActivity() {
    var binding: ActivityRegisterPageOneBinding? = null
    var auth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var storage: FirebaseStorage? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageOneBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()

        binding!!.back.setOnClickListener { onBackPressed() }
        binding!!.continueBtn.setOnClickListener {
            val fullName: String = binding!!.fullNameEt.text.toString()
            val userName: String = binding!!.usernameEt.text.toString()
            val bio: String = binding!!.bioEt.text.toString()
            if(fullName.isEmpty())
                binding!!.fullNameEt.error = "Please type your full name"
            if(userName.isEmpty())
                binding!!.usernameEt.error = "Please type a username"
            if(bio.isEmpty())
                binding!!.bioEt.error = "Please type something about yourself"

            val intent = Intent(this@RegisterPageOne, RegisterFinalPage::class.java)
            intent.putExtra("full_name", fullName)
            intent.putExtra("user_name", userName)
            intent.putExtra("bio", bio)
            startActivity(intent)
        }
    }
}