package com.example.shoppingapp.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.shoppingapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        lifecycleScope.launchWhenStarted {
            viewModel.isUserExist.collect { isCurrentUserExist ->
                if (isCurrentUserExist != null) {
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
                    val navController = navHostFragment.navController
                    val navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)
                    if (isCurrentUserExist) {
                        navGraph.setStartDestination(R.id.tabsFragment)
                    } else
                        navGraph.setStartDestination(R.id.signInFragment)
                    navController.graph = navGraph
                }
            }

        }
        super.onStart()
    }
}