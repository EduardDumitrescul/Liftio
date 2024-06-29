package com.example.fitnesstracker.view.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.view.theme.AppTheme

@Composable
fun FilledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = buttonColors,
        shape = AppTheme.shapes.roundedSmallCornerShape
    ) {
        Text(
            text = text,
            style = AppTheme.typography.body,
            )
    }
}

private val buttonColors @Composable
get() = ButtonColors(
    contentColor = AppTheme.colors.onPrimary,
    containerColor = AppTheme.colors.primary,
    disabledContentColor = AppTheme.colors.onPrimaryVariant,
    disabledContainerColor = AppTheme.colors.primaryVariant
)

@Preview
@Composable
fun PreviewFilledButton() {
    AppTheme {
        FilledButton(text = "Action", onClick = {})
    }
}