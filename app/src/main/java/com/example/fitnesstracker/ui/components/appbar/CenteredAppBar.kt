package com.example.fitnesstracker.ui.components.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.components.button.IconButton
import com.example.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Title(title)
        },
        modifier = modifier,
        colors = appBarColors,
        navigationIcon = {
            BackNavigationIcon(
                onClick = onNavigationIconClick,
                color = AppTheme.colors.onBackground
            )
        },
        actions = actions,
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun Title(title: String) {
    Text(
        title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = AppTheme.typography.title,
        color = AppTheme.colors.onBackground
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun CenteredAppBarPreview() {
    CenteredAppBar(
        title = "Pull Workout",
        actions = {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Rounded.CheckCircleOutline,
                contentDescription = "",
                containerColor = Color.Transparent,
                contentColor = AppTheme.colors.primary,
                size = 40.dp
            )
        }
    )
}