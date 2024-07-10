package com.example.fitnesstracker.ui.views.exercise.browse

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.R
import com.example.fitnesstracker.ui.views.exercise.browse.EquipmentType.Barbell
import com.example.fitnesstracker.ui.views.exercise.browse.EquipmentType.Cables
import com.example.fitnesstracker.ui.views.exercise.browse.EquipmentType.Dumbbell
import com.example.fitnesstracker.ui.views.exercise.browse.EquipmentType.Machine
import com.example.fitnesstracker.ui.views.exercise.browse.EquipmentType.None
import com.example.fitnesstracker.ui.theme.AppTheme

@Composable
fun EquipmentIcon(
    equipment: String = ""
) {
    val type = getType(equipment)
    Surface(
        shape = AppTheme.shapes.roundedSmallCornerShape,
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
    Dumbbell(R.drawable.dumbbell, "dumbbells"),
    Barbell(R.drawable.barbell, "barbell"),
    Machine(R.drawable.machine, "machine"),
    Cables(R.drawable.cables, "cables"),
    None(R.drawable.none, "none")
}

private fun getType(type: String): EquipmentType {
    return when(type) {
        "dumbbells" -> Dumbbell
        "barbell" -> Barbell
        "machine" -> Machine
        "cables" -> Cables
        else -> None
    }
}

@Composable
@Preview
private fun PreviewEquipmentIcon() {
    AppTheme {
        EquipmentIcon()
    }
}