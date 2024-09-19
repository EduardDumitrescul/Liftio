package com.thinkerbyte.fitnesstracker.ui.components.appbar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    showNavigationIcon: Boolean = true,
    actions: @Composable RowScope.() -> Unit = {},
) {

    val targetElevation = scrollBehavior.state.collapsedFraction * 4.dp
    val elevation by animateDpAsState(targetValue = targetElevation, label = "")
    Surface(
        color = appBarColors.containerColor,
        shadowElevation = elevation,
    ) {
        MediumTopAppBar(
            modifier = Modifier
                .then(modifier),
            colors = appBarColors,
            title = { AppBarTitle(title) },
            navigationIcon = {
                if(showNavigationIcon) {
                    BackNavigationIcon(onClick = onNavigationIconClick)
                }
            },
            actions = actions,
            scrollBehavior = scrollBehavior,
        )
    }

}

@Composable
private fun AppBarTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        title,
        modifier = modifier,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style= AppTheme.typography.title,
        color= AppTheme.colors.onBackground
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun LargeAppBarPreview() {
    LargeAppBar(title = "Top App Bar")
}