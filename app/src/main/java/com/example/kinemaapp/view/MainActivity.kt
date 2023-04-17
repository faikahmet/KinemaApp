package com.example.kinemaapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kinemaapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController= findNavController(R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment,
                R.id.movieFragment
            )
        )
       findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfiguration)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}