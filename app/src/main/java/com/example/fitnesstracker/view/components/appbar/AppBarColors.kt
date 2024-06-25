package com.example.fitnesstracker.view.components.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.fitnesstracker.view.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
val appBarColors
    @Composable get() = TopAppBarColors(
    containerColor = Color.Transparent,
    scrolledContainerColor = AppTheme.colors.background,
    navigationIconContentColor = AppTheme.colors.onBackground,
    titleContentColor = AppTheme.colors.onBackground,
    actionIconContentColor = AppTheme.colors.onBackground,
)