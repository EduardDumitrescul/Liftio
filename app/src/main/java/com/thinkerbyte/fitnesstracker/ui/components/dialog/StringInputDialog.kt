package com.thinkerbyte.fitnesstracker.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.thinkerbyte.fitnesstracker.ui.components.button.TwoButtonRow
import com.thinkerbyte.fitnesstracker.ui.components.card.LargeCard
import com.thinkerbyte.fitnesstracker.ui.components.card.LargeCardDefaults
import com.thinkerbyte.fitnesstracker.ui.components.textfield.FilledTextField
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme
import com.thinkerbyte.fitnesstracker.ui.theme.AppThemeNoViewModel

@Composable
fun StringInputDialog(
    title: String,
    initialValue: String,
    onDismissRequest: () -> Unit,
    onSave: (String) -> Unit,
) {
    var value by remember {
        mutableStateOf(initialValue)
    }

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        LargeCard(
            onClick = {},
            colors = LargeCardDefaults.colors.copy(
                containerColor = AppTheme.colors.background,
                contentColor = AppTheme.colors.onBackground
            ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = AppTheme.typography.headline,

                )
                FilledTextField(
                    text = value,
                    onValueChange = {value = it}
                )

                TwoButtonRow(
                    primaryButtonText = "Save",
                    onPrimaryButtonClick = {
                        onSave(value)
                        onDismissRequest()
                    },
                    secondaryButtonText = "Cancel",
                    onSecondaryButtonClick = onDismissRequest
                )
            }


        }
    }
}


@Composable
@Preview
fun PreviewStringInputDialog() {
    AppThemeNoViewModel() {
        StringInputDialog(
            title = "Choose a name",
            onDismissRequest = {},
            onSave = {},
            initialValue = ""
        )
    }
}