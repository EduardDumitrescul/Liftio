package com.example.fitnesstracker.ui.views.settings

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnesstracker.data.datastore.Theme
import com.example.fitnesstracker.ui.components.appbar.LargeAppBar
import com.example.fitnesstracker.ui.components.dialog.ConfirmationDialog
import com.example.fitnesstracker.ui.theme.AppTheme

//TODO option to erase data

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
            ThemeRow(
                state.theme,
                themeDropdownExpanded,
                showDropdown = {themeDropdownExpanded = true},
                hideDropdown = {themeDropdownExpanded = false},
                updateTheme = {viewModel.updateTheme(it)}
            )

            ClearHistoryRow(
                onClearHistory = {
                    viewModel.clearHistory()
                }
            )

        }
    }
}

@Composable
private fun ClearHistoryRow(
    onClearHistory: () -> Unit,
) {
    var showConfirmationToast by remember { mutableStateOf(false) }
    if(showConfirmationToast) {
        Toast.makeText(LocalContext.current, "Your history has been cleared!", Toast.LENGTH_SHORT).show()
        showConfirmationToast = false
    }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    SettingsRow(
        onClick = {showConfirmationDialog = true}
    ) {
        Text(
            text = "Clear History",
            style = AppTheme.typography.body,
            color = AppTheme.colors.onBackground
        )
    }

    if(showConfirmationDialog) {
        ConfirmationDialog(
            mainText = "Erase History?",
            secondaryText = "Everything will be permanently deleted",
            confirmText = "Clear history",
            cancelText = "No, go back",
            onCancel = {showConfirmationDialog = false},
            onConfirm = {
                onClearHistory()
                showConfirmationDialog = false
                showConfirmationToast = true
                        },
            onDismissRequest = {showConfirmationDialog = false}
        )
    }
}

@Composable
private fun ThemeRow(
    theme: Theme,
    themeDropdownExpanded: Boolean,
    showDropdown: () -> Unit,
    hideDropdown: () -> Unit,
    updateTheme: (Theme) -> Unit,
) {
    SettingsRow(
        onClick = showDropdown
    ) {
        Text(
            text = "Theme",
            style = AppTheme.typography.body,
            color = AppTheme.colors.onBackground
        )
        Box {
            Text(
                theme.name,
                style = AppTheme.typography.caption,
                color = AppTheme.colors.onBackground
            )
            DropdownMenu(
                expanded = themeDropdownExpanded,
                onDismissRequest = hideDropdown,
                containerColor = AppTheme.colors.container
            ) {
                for (themeIterator in Theme.entries) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = themeIterator.name,
                                style = AppTheme.typography.body,
                                color = AppTheme.colors.onBackground
                            )
                        },
                        onClick = {updateTheme(themeIterator)}
                    )
                }
            }
        }
    }
}