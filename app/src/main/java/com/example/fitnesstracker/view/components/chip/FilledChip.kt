package com.example.fitnesstracker.view.components.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.view.theme.AppTheme

@Composable
fun FilledChip(
    label: String,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier
            .clip(chipShape)
            .background(filledChipColors.containerColor)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Label(label)
    }
}

private val filledChipColors
@Composable get() = object {
    val containerColor = AppTheme.colors.primaryVariant
    val labelColor = AppTheme.colors.onPrimaryVariant
}

@Composable
private fun Label(
    text: String
) {
    Text(
        text,
        color = filledChipColors.labelColor,
        style = AppTheme.typography.caption
    )
}

@Preview(showBackground = true)
@Composable
fun FilledChipPreview() {
    FilledChip(label = "filled chip")
}