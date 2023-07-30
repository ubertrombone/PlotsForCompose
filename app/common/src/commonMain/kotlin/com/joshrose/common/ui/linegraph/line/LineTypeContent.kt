package com.joshrose.common.ui.linegraph.line

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.common.util.ImageResources.LINE_CURVE
import com.joshrose.common.util.ImageResources.LINE_STRAIGHT
import com.joshrose.common.util.createPainter
import com.joshrose.plotsforcompose.linegraph.util.LineType

@Composable
fun LineTypeContent(
    currentSelected: LineType,
    onSelected: (LineType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Line Type",
            color = MaterialTheme.colorScheme.primary,
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        )

        Spacer(Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: Update selected colors
            OutlinedIconToggleButton(
                checked = currentSelected == LineType.STRAIGHT,
                onCheckedChange = { onSelected(LineType.STRAIGHT) },
            ) {
                Icon(painter = createPainter(LINE_STRAIGHT), contentDescription = "Straight Line Type")
            }

            // TODO: Update selected colors
            OutlinedIconToggleButton(
                checked = currentSelected == LineType.CURVED,
                onCheckedChange = { onSelected(LineType.CURVED) },
            ) {
                Icon(painter = createPainter(LINE_CURVE), contentDescription = "Curved Line Type")
            }
        }
    }
}