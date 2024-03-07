package com.example.fitnesstracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.theme.AppTheme

data class Item(
    val text: String,
    val icon: ImageVector
)

private val items = listOf(
    Item("Home", Icons.Rounded.Home),
    Item("Exercises", Icons.Rounded.FitnessCenter),
    Item("Stats", Icons.Rounded.BarChart),
    Item("History", Icons.Rounded.History),
)

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    Surface(
        shadowElevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(AppTheme.colors.container),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                NavItem(
                    modifier = Modifier.weight(1f),
                    icon = item.icon,
                    text = item.text,
                    selected = selectedItem == index,
                    onClick = {selectedItem = index}
                )
            }
        }
    }

}

@Composable
private fun NavItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    val color = if(selected) AppTheme.colors.primary else AppTheme.colors.primaryVariant
    val interactionSource = remember { MutableInteractionSource() }

    Box (
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.height(48.dp)
        ) {
        Icon(
            icon,
            contentDescription = text,
            modifier.size(32.dp),
            tint = color)
            Text(
                text = text,
                style=AppTheme.typography.body,
                color=color)
        }
    }
}

@Preview (showBackground = true, widthDp = 480)
@Composable
fun BottomNavBarPreview() {
    AppTheme {
        BottomNavBar()
    }
}