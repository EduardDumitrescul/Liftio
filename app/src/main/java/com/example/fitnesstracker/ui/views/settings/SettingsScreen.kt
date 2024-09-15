package com.example.fitnesstracker.ui.views.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.data.datastore.Theme
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var themeDropdownExpanded by remember { mutableStateOf(false) }
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .clickable { themeDropdownExpanded = !themeDropdownExpanded }
                    .padding(16.dp)
            ) {
                Text(text = "Theme", style = AppTheme.typography.body, color = AppTheme.colors.onBackground)
                Box() {
                    Text(state.theme.name, style = AppTheme.typography.caption, color = AppTheme.colors.onBackground)
                    DropdownMenu(
                        expanded = themeDropdownExpanded,
                        onDismissRequest = { themeDropdownExpanded = false },
                        containerColor = AppTheme.colors.container,
                        modifier = Modifier.clickable { themeDropdownExpanded = true }
                    ) {
                        DropdownMenuItem(
                            text = {Text(text = "light", style = AppTheme.typography.body, color = AppTheme.colors.onBackground)},
                            onClick = {viewModel.updateTheme(Theme.LIGHT)}
                        )
                        DropdownMenuItem(
                            text = {Text(text = "dark", style = AppTheme.typography.body, color = AppTheme.colors.onBackground)},
                            onClick = {viewModel.updateTheme(Theme.DARK)}
                        )
                        DropdownMenuItem(
                            text = {Text(text = "system", style = AppTheme.typography.body, color = AppTheme.colors.onBackground)},
                            onClick = {viewModel.updateTheme(Theme.SYSTEM)}
                        )
                    }
                }

            }

        }
    }
}