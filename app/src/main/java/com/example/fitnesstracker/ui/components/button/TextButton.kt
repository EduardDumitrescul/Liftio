package com.example.fitnesstracker.ui.components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun TextButton(
    text: String,
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = text,
            Modifier.size(20.dp),
            tint = AppTheme.colors.primary)
        Text(
            text = text,
            style = AppTheme.typography.body,
            color = AppTheme.colors.primary)
    }
}

@Preview
@Composable
fun PreviewIconButton() {
    AppTheme() {
        TextButton(
            text = "add",
            imageVector = Icons.Rounded.Add,
            onClick = {})
    }
}