package com.example.fitnesstracker.view.components.appbar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredAppBar(
    modifier: Modifier = Modifier,
    title: String
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    CenterAlignedTopAppBar(
        title = { AppBarTitle(title) },
        modifier = modifier,
        colors = appBarColors,
        navigationIcon = { BackNavigationIcon(onClick = { /*TODO*/ })},
        actions = { MoreActionsIcon(onClick = { /*TODO*/ }) },
        scrollBehavior = scrollBehavior
    )
}

@Composable
@Preview
fun CenteredAppBarPreview() {
    CenteredAppBar(title = "App Bar")
}