package com.example.fitnesstracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.fitnesstracker.data.AppDatabase
import com.example.fitnesstracker.nav.AppNavHost
import com.example.fitnesstracker.nav.BottomNavBar
import com.example.fitnesstracker.nav.bottomNavBarDestinations
import com.example.fitnesstracker.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MAIN ACTIVITY"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db.testDao().getAll().observeForever {
            Log.d(TAG, it.toString())
        }



        setContent {
            AppTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(
                            bottomNavBarDestinations.toList(),
                            navController = navController)
                    }
                ) {
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}