package com.example.fitnesstracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeAppBar(
    modifier: Modifier = Modifier,
    text: String,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    MediumTopAppBar(
        modifier = modifier.background(AppTheme.colors.container),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = AppTheme.colors.background,
            titleContentColor = AppTheme.colors.onBackground,
            scrolledContainerColor = AppTheme.colors.background,
        ),
        title = {
            Text(
                text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = "Go back"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "Options"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Preview
@Composable
private fun LargeAppBarPreview() {
    LargeAppBar(text = "Medium Top App Bar")
}