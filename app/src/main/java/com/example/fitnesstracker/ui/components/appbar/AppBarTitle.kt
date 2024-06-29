package com.example.fitnesstracker.ui.components.appbar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.example.fitnesstracker.ui.theme.AppTheme

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