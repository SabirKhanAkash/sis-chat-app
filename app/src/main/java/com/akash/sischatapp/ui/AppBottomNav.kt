package com.akash.sischatapp.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.akash.sischatapp.R
import com.akash.sischatapp.databinding.ActivityAppBottomNavBinding

class AppBottomNav : AppCompatActivity() {

    private lateinit var binding: ActivityAppBottomNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityAppBottomNavBinding.inflate(layoutInflater)//
        setContentView(R.layout.activity_app_bottom_nav)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_app_bottom_nav) as NavHostFragment
        val navController = navHostFragment.navController

        setupWithNavController(navView, navController)
    }
}