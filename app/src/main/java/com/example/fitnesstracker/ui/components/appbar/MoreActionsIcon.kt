package com.example.fitnesstracker.ui.components.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun MoreActionsIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        colors = moreActionIconColors
    ) {
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "Options",
        )
    }
}

private val moreActionIconColors
    @Composable get() = IconButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.onBackground,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = AppTheme.colors.onBackground
    )