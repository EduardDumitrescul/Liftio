package com.example.fitnesstracker.ui.components.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    showNavigationIcon: Boolean = true,
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { AppBarTitle(title) },
        modifier = modifier,
        colors = appBarColors,
        navigationIcon = { BackNavigationIcon(onClick = onNavigationIconClick) },
        actions = actions,
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CenteredAppBarPreview() {
    CenteredAppBar(title = "App Bar")
}