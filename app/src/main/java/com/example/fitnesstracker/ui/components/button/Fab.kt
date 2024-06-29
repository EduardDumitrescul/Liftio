package com.example.fitnesstracker.ui.components.button

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.theme.AppTheme

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
        Icon(imageVector, description, modifier = Modifier.size(40.dp))
    }
}

@Composable
@Preview
private fun PreviewFab() {
    AppTheme {
        Fab(imageVector = Icons.Rounded.Add, description = "add", onClick = {} )
    }
}