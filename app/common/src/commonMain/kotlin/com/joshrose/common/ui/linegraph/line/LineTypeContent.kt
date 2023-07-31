package com.joshrose.common.ui.linegraph.line

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
            horizontalArrangement = Arrangement.spacedBy(40.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedIconToggleButton(
                checked = currentSelected == LineType.STRAIGHT,
                onCheckedChange = { onSelected(LineType.STRAIGHT) },
                colors = IconButtonDefaults.iconToggleButtonColors(
                    checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    checkedContentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.size(28.dp)
            ) {
                Icon(painter = createPainter(LINE_STRAIGHT), contentDescription = "Straight Line Type")
            }

            OutlinedIconToggleButton(
                checked = currentSelected == LineType.CURVED,
                onCheckedChange = { onSelected(LineType.CURVED) },
                colors = IconButtonDefaults.iconToggleButtonColors(
                    checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    checkedContentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.size(28.dp)
            ) {
                Icon(painter = createPainter(LINE_CURVE), contentDescription = "Curved Line Type")
            }
        }
    }
}