package com.example.fitnesstracker.view.components.appbar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.example.fitnesstracker.view.theme.AppTheme

@Composable
fun AppBarTitle(
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