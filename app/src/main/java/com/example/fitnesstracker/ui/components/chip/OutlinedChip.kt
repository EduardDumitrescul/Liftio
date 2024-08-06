package com.example.fitnesstracker.ui.components.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.ui.theme.AppTheme


@Composable
fun OutlinedChip(
    label: String,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier
            .border(AppTheme.dimensions.border, outlinedChipColors.outline, AppTheme.shapes.roundedSmallCornerShape)
            .clip(AppTheme.shapes.roundedSmallCornerShape)
            .background(outlinedChipColors.container)
            .padding(vertical = AppTheme.dimensions.paddingSmall, horizontal = AppTheme.dimensions.paddingNormal),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Label(label)
    }
}

private val outlinedChipColors
    @Composable get() = object {
        val container = AppTheme.colors.containerVariant
        val label = AppTheme.colors.onContainerVariant
        val outline = AppTheme.colors.outline
    }

@Composable
private fun Label(
    text: String
) {
    Text(
        text,
        color = outlinedChipColors.label,
        style = AppTheme.typography.caption
    )
}

@Preview(showBackground = true)
@Composable
fun OutlinedChipPreview() {
    OutlinedChip(label = "outlined chip")
}