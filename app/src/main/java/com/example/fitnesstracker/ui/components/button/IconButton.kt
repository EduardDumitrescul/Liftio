package com.example.fitnesstracker.ui.components.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    containerColor: Color,
    contentColor: Color,
) {
    androidx.compose.material3.IconButton(
        onClick = onClick,
        modifier = modifier,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
fun PreviewIconButton2() {
    AppTheme {
        IconButton(
            onClick = {},
            imageVector = Icons.Rounded.RemoveCircle,
            contentDescription = "",
            contentColor = AppTheme.colors.primary,
            containerColor = AppTheme.colors.onPrimary
        )
    }
}