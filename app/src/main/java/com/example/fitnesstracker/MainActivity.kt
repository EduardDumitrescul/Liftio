package com.example.fitnesstracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitnesstracker.data.roomdb.AppDatabase
import com.example.fitnesstracker.ui.nav.AppNavHost
import com.example.fitnesstracker.ui.nav.BottomNavBar
import com.example.fitnesstracker.ui.nav.bottomNavBarDestinations
import com.example.fitnesstracker.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MAIN ACTIVITY"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var db: AppDatabase

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute != null) {
                            Log.d(TAG, currentRoute)
                        }
                        if(currentRoute in bottomNavBarDestinations.map{it.route})
                        BottomNavBar(
                            bottomNavBarDestinations.toList(),
                            navController = navController)
                    }
                ) { innerPaddings ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPaddings)
                    )
                }
            }
        }
    }
}