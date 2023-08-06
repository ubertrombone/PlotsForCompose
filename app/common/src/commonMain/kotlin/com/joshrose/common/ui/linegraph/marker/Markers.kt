package com.joshrose.common.ui.linegraph.marker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.common.util.ImageResources.SMALL_PUSH_PIN
import com.joshrose.common.util.createPainter

@Composable
fun Markers(
    selected: Boolean,
    onSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Show Markers",
            color = MaterialTheme.colorScheme.primary,
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        )

        Spacer(Modifier.height(5.dp))

        OutlinedIconToggleButton(
            checked = selected,
            onCheckedChange = { onSelected(!selected) },
            colors = IconButtonDefaults.outlinedIconToggleButtonColors(
                checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                checkedContentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.size(28.dp)
        ) {
            Icon(painter = createPainter(SMALL_PUSH_PIN), contentDescription = "Show Markers")
        }
    }
}