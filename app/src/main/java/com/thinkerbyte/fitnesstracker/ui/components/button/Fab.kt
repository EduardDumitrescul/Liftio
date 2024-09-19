package com.thinkerbyte.fitnesstracker.ui.components.button

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme

@Composable
fun Fab(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        shape = AppTheme.shapes.roundedFullCornerShape,
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary
    ) {
        Icon(imageVector, description, modifier = Modifier.size(AppTheme.dimensions.iconLarge))
    }
}

@Composable
@Preview
private fun PreviewFab() {
    AppTheme {
        Fab(imageVector = Icons.Rounded.Add, description = "add", onClick = {} )
    }
}