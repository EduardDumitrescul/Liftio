package com.example.fitnesstracker.ui.nav

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun BottomNavBar(
    destinations: List<NavDestination>,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Surface(
        shadowElevation = AppTheme.dimensions.elevationHigh,
        color = AppTheme.colors.container,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            destinations.forEachIndexed { index, item ->
                NavItem(
                    modifier = Modifier.weight(1f),
                    icon = item.icon,
                    text = item.name,
                    selected = currentDestination?.hierarchy?.any {
                        it.route == item.route
                    } == true,
                    onClick = {
                        selectedItem = index
                        navController.navigate(item.route)
                    }
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
    val color = if(selected) AppTheme.colors.primary else AppTheme.colors.outline
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
            modifier = Modifier.height(40.dp)
        ) {
        Icon(
            icon,
            contentDescription = text,
            modifier.size(AppTheme.dimensions.iconNormal),
            tint = color)
            Text(
                text = text,
                style= AppTheme.typography.body,
                color=color)
        }
    }
}

@Preview(heightDp = 300)
@Composable
private fun BottomNavBarPreview() {
    AppTheme {
        Column(verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(Color(0xFFFFFFFF))) {
            BottomNavBar(destinations = bottomNavBarDestinations.toList(), navController = rememberNavController())
        }

    }
}