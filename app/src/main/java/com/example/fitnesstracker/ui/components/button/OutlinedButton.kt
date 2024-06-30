package com.example.fitnesstracker.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = buttonColors,
        shape = AppTheme.shapes.roundedSmallCornerShape,
        border = BorderStroke(
            width = AppTheme.dimensions.borderWide,
            color = AppTheme.colors.primary,
        )
    ) {
        Text(
            text = text,
            style = AppTheme.typography.body,
        )
    }
}

private val buttonColors @Composable
get() = ButtonColors(
        contentColor = AppTheme.colors.primary,
        containerColor = Color.Transparent,
        disabledContentColor = AppTheme.colors.primary,
        disabledContainerColor = Color.Transparent
    )

@Preview(showBackground = true)
@Composable
fun PreviewOutlinedButton() {
    AppTheme {
        OutlinedButton(text = "Action", onClick = {})
    }
}