package com.example.fitnesstracker.ui.views.workout.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnesstracker.ui.theme.AppTheme
import java.util.Locale

@Composable
fun Timer(
    elapsedTime: Long,
    modifier: Modifier = Modifier
) {
    Text(
        text = formatElapsedTime(elapsedTime),
        style = AppTheme.typography.display,
        color = AppTheme.colors.onBackground,
        modifier = modifier
    )
}

fun formatElapsedTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    if(hours > 0) {
        return String.format(Locale.getDefault(), "%01d:%02d:%02d", hours, minutes, secs)
    }
    else {
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, secs)
    }
}

@Preview
@Composable
fun PreviewTimer() {
    AppTheme {
        Timer(4567)
    }
}