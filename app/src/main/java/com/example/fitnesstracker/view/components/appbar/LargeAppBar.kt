package com.example.fitnesstracker.view.components.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeAppBar(
    modifier: Modifier = Modifier,
    title: String,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    TopAppBar(
        modifier = modifier,
        colors = appBarColors,
        title = { AppBarTitle(title) },
        navigationIcon = { BackNavigationIcon(onClick = { /*TODO*/ }) },
        actions = { MoreActionsIcon(onClick = { /*TODO*/ })},
        scrollBehavior = scrollBehavior
    )
}

@Preview
@Composable
private fun LargeAppBarPreview() {
    LargeAppBar(title = "Top App Bar")
}