package com.example.fitnesstracker.ui.components.appbar

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardBackspace
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun BackNavigationIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colors.onBackground,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        colors = backNavigationIconColors.copy(
            contentColor = color
        )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardBackspace,
            contentDescription = "Go back",
            modifier = Modifier.size(40.dp)
        )
    }
}

private val backNavigationIconColors
    @Composable get() = IconButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.onBackground,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = AppTheme.colors.onBackground
    )