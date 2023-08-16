package com.akash.sischatapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akash.sischatapp.databinding.ActivityRegisterPageOneBinding
import com.akash.sischatapp.util.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.regex.Pattern


class RegisterPageOne : AppCompatActivity() {
    private val sharedPref: SharedPref = SharedPref()
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
        database = FirebaseDatabase.getInstance("https://sis-chat-app-d78c3-default-rtdb.asia-southeast1.firebasedatabase.app")

        binding!!.back.setOnClickListener { onBackPressed() }
        binding!!.continueBtn.setOnClickListener {
            val fullName: String = binding!!.fullNameEt.text.toString()
            val userName: String = binding!!.usernameEt.text.toString()
            val bio: String = binding!!.bioEt.text.toString()

            val isFullNameValid = Pattern.compile("^[a-zA-Z ]+$").matcher(fullName).matches()
            val isUserNameValid = Pattern.compile("^[a-zA-Z0-9._]+$").matcher(userName).matches()
            if (fullName.isEmpty() || !isFullNameValid)
                binding!!.fullNameEt.error = "Please type your full valid name"
            else
                sharedPref.setString(applicationContext, "full_name", fullName)
            if (userName.isEmpty() || !isUserNameValid)
                binding!!.usernameEt.error = "Please type a valid username"
            else
                sharedPref.setString(applicationContext, "user_name", userName)
            if (bio.isEmpty())
                binding!!.bioEt.error = "Please type something about yourself"
            else
                sharedPref.setString(applicationContext, "bio", bio)

            if (fullName.isNotEmpty() && userName.isNotEmpty() && bio.isNotEmpty()) {
                startActivity(Intent(this@RegisterPageOne, RegisterFinalPage::class.java))
            }

        }
    }
}