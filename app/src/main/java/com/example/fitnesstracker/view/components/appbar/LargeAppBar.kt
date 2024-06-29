package com.example.fitnesstracker.view.components.appbar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
) {
    MediumTopAppBar(
        modifier = modifier,
        colors = appBarColors,
        title = { AppBarTitle(title) },
        navigationIcon = { BackNavigationIcon(onClick = onNavigationIconClick) },
        actions = { MoreActionsIcon(onClick = { /*TODO*/ })},
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun LargeAppBarPreview() {
    LargeAppBar(title = "Top App Bar")
}