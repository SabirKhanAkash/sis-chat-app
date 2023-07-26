package com.akash.sischatapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.ActivityAppBottomNavBinding

class AppBottomNav : AppCompatActivity() {
    private lateinit var navController: NavController
    private var binding: ActivityAppBottomNavBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBottomNavBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding!!.bottomNavigationView, navController)
    }
}