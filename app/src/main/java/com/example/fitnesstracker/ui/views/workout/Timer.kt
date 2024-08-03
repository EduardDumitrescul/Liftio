package com.example.fitnesstracker.ui.views.workout

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun Timer(
    elapsedTime: Long,
    modifier: Modifier = Modifier
) {
    Text(
        text = formatElapsedTime(elapsedTime),
        style = AppTheme.typography.display,
        color = AppTheme.colors.primary,
        modifier = modifier
    )
}

fun formatElapsedTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("%01d:%02d:%02d", hours, minutes, secs)
}

@Preview
@Composable
fun PreviewTimer() {
    AppTheme {
        Timer(4567)
    }
}