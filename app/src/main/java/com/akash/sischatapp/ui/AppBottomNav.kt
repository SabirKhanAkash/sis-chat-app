package com.akash.sischatapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.ActivityAppBottomNavBinding
import com.akash.sischatapp.model.User
import com.akash.sischatapp.util.LoadingDialog
import com.akash.sischatapp.util.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import showTopToast

class AppBottomNav : AppCompatActivity() {
    private val loadingDialog: LoadingDialog = LoadingDialog(this@AppBottomNav)
    private lateinit var navController: NavController
    private val sharedPref: SharedPref = SharedPref()
    private var binding: ActivityAppBottomNavBinding? = null
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBottomNavBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        auth = FirebaseAuth.getInstance()
        database =
            FirebaseDatabase.getInstance("https://sis-chat-app-d78c3-default-rtdb.asia-southeast1.firebasedatabase.app")

        loadingDialog.startLoading()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue<User>()
                sharedPref.setString(applicationContext, "full_name", post!!.fullName.toString())
                sharedPref.setString(applicationContext, "user_name", post.userName.toString())
                sharedPref.setString(applicationContext, "bio", post.bio.toString())
                sharedPref.setString(applicationContext, "dp", post.profileImage.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showTopToast(
                    applicationContext, databaseError.toException().toString(), "short", "negative"
                )
            }
        }
        database.reference.child(
            "users"
        ).child(
            auth.currentUser!!.uid
        ).addValueEventListener(postListener)

        loadingDialog.dismissLoading()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding!!.bottomNavigationView, navController)
    }

    override fun onResume() {
        super.onResume()
        val currentId = FirebaseAuth.getInstance().uid
        database.reference.child("presence")
            .child(currentId!!).setValue("Online")
    }

    override fun onPause() {
        super.onPause()
        val currentId = FirebaseAuth.getInstance().uid
        database.reference.child("presence")
            .child(currentId!!).setValue("Offline")
    }
}