package com.batya.surftest.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.batya.surftest.R
import com.batya.surftest.app.util.gone
import com.batya.surftest.app.util.visible
import com.batya.surftest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_myCocktails_to_editCocktail)
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id == R.id.myCocktailsFragment) {
                binding.coordinatorLayout.visible()
            } else {
                binding.coordinatorLayout.gone()

            }
        }
    }

}