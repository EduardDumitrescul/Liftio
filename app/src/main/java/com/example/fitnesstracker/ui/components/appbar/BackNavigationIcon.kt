package com.example.fitnesstracker.ui.components.appbar

import android.service.autofill.OnClickAction
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun BackNavigationIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        colors = backNavigationIconColors
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBackIosNew,
            contentDescription = "Go back"
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