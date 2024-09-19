package com.thinkerbyte.fitnesstracker.ui.views.exercise.browse

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thinkerbyte.fitnesstracker.ui.components.textfield.FilledTextField
import com.thinkerbyte.fitnesstracker.ui.theme.AppTheme

@Composable
fun SearchField(
    searchValue: String,
    onValueChange: (String) -> Unit,
) {
    FilledTextField(
        text = searchValue,
        onValueChange = onValueChange,
        placeholderText = "exercise name",
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "search",
                modifier = Modifier.size(AppTheme.dimensions.iconSmall)
            )
        }
    )
}