package com.thinkerbyte.fitnesstracker.ui.components.card

import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme

@Composable
fun LargeCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CardColors = LargeCardDefaults.colors,
    content: @Composable (() -> Unit),
) {
    ElevatedCard(
        onClick = onClick,
        shape = AppTheme.shapes.roundedNormalCornerShape,
        colors = colors,
        elevation = cardElevation,
        modifier = modifier
    ) {
        content()
    }
}

object LargeCardDefaults {
    val colors @Composable get() = CardColors(
        contentColor = AppTheme.colors.onContainer,
        containerColor = AppTheme.colors.container,
        disabledContentColor = AppTheme.colors.onContainer,
        disabledContainerColor = AppTheme.colors.container,
    )
}

private val cardElevation @Composable get() = CardDefaults.elevatedCardElevation()