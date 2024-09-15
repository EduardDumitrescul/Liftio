package com.example.fitnesstracker.ui.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            LargeAppBar(
                title = "Settings",
                scrollBehavior = scrollBehavior,
                onNavigationIconClick = navigateBack
            )
        },
        containerColor = AppTheme.colors.background,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)

    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

        }
    }
}