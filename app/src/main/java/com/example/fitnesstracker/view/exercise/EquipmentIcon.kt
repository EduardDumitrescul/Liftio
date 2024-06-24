package com.example.fitnesstracker.view.exercise

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.R
import com.example.fitnesstracker.view.theme.AppTheme

@Composable
fun EquipmentIcon(
    type: EquipmentType
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = AppTheme.colors.containerVariant,
    ) {
       Icon(
            painter = painterResource(id = type.id),
            contentDescription = type.description,
            tint = AppTheme.colors.onContainerVariant,
           modifier = Modifier.size(48.dp)
        )

    }
}


enum class EquipmentType(val id: Int, val description: String) {
    Dumbbell(R.drawable.dumbbell, "dumbbell"),
    Barbell(R.drawable.barbell, "barbell"),
    Machine(R.drawable.machine, "machine"),
    Cables(R.drawable.cables, "cables"),
    None(R.drawable.none, "none"),
}

@Composable
@Preview
private fun PreviewEquipmentIcon() {
    AppTheme {
        EquipmentIcon(type = EquipmentType.None)
    }
}