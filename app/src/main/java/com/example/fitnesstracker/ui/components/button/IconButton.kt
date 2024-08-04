package com.example.fitnesstracker.ui.components.button

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    containerColor: Color,
    contentColor: Color,
    size: Dp = 24.dp
) {

    FilledIconButton(
        shape = AppTheme.shapes.roundedSmallCornerShape,
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.size(size)
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