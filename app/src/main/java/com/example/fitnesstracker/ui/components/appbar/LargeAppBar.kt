package com.example.fitnesstracker.ui.components.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    showNavigationIcon: Boolean = true,
    actions: @Composable RowScope.() -> Unit = {},
) {
    MediumTopAppBar(
        modifier = modifier,
        colors = appBarColors,
        title = { AppBarTitle(title) },
        navigationIcon = {
            if(showNavigationIcon) {
                BackNavigationIcon(onClick = onNavigationIconClick)
            }
                         },
        actions = actions,
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun AppBarTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        title,
        modifier = modifier,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style= AppTheme.typography.title,
        color= AppTheme.colors.onBackground
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun LargeAppBarPreview() {
    LargeAppBar(title = "Top App Bar")
}