package com.joshrose.common.ui.linegraph.line

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.linegraph.util.LineType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
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
                Icon(painter = painterResource("line_straight.xml"), contentDescription = "Straight Line Type")
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
                Icon(painter = painterResource("line_curve.xml"), contentDescription = "Curved Line Type")
            }
        }
    }
}