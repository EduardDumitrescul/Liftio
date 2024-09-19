package com.thinkerbyte.fitnesstracker.ui.components.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
val appBarColors
    @Composable get() = TopAppBarColors(
    containerColor = AppTheme.colors.background,
    scrolledContainerColor = AppTheme.colors.background,
    navigationIconContentColor = AppTheme.colors.onBackground,
    titleContentColor = AppTheme.colors.onBackground,
    actionIconContentColor = AppTheme.colors.onBackground,
)