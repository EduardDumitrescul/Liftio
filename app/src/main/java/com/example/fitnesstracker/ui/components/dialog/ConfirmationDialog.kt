package com.example.fitnesstracker.ui.components.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.fitnesstracker.ui.components.button.TwoButtonRow
import com.example.fitnesstracker.ui.components.card.LargeCard
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun ConfirmationDialog(
    mainText: String,
    secondaryText: String,
    cancelText: String,
    confirmText: String,
    isWarning: Boolean = false,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        LargeCard(
            onClick = {},
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = mainText,
                    style = AppTheme.typography.headline,
                    color = AppTheme.colors.onContainer,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
                )
                Text(
                    text = secondaryText,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.onContainer,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(top = 32.dp), color = AppTheme.colors.outline)

                Row(modifier = Modifier.height(40.dp)) {
                    Button(
                        onClick = onCancel,
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = Color.Transparent,
                            contentColor = AppTheme.colors.onContainer
                        ),
                    ) {
                        Text(
                            text = cancelText,
                            style = AppTheme.typography.body,
                            color = AppTheme.colors.onContainer
                        )
                    }
                    VerticalDivider(thickness = 1.dp, color = AppTheme.colors.outline)
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = Color.Transparent,
                            contentColor = AppTheme.colors.onContainer
                        ),
                    ) {
                        Text(
                            text = confirmText,
                            style = AppTheme.typography.body,
                            color = if(isWarning) AppTheme.colors.red else AppTheme.colors.green
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewConfirmationDialog() {
    AppTheme {
        ConfirmationDialog(
            mainText = "Confirm?",
            secondaryText = "",
            confirmText = "Yes",
            cancelText = "Cancel",
            onDismissRequest = {},
            onConfirm = {},
            onCancel = {},
        )
    }
}