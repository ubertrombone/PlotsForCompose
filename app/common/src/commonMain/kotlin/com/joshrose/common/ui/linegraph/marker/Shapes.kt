package com.joshrose.common.ui.linegraph.marker

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.joshrose.common.ui.linegraph.marker.marker_shapes.*
import com.joshrose.plotsforcompose.util.Markers
import com.joshrose.plotsforcompose.util.Markers.*

@Composable
fun Shapes(
    selected: Markers,
    onSelected: (Markers) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Marker Shape",
            color = colorScheme.primary,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            circle.draw(properties = CanvasProperties(
                color = selectionColor(selected == CIRCLE),
                action = { onSelected(it) }
            ))
            square.draw(properties = CanvasProperties(
                color = selectionColor(selected == SQUARE),
                action = { onSelected(it) }
            ))
            triangle.draw(properties = CanvasProperties(
                color = selectionColor(selected == TRIANGLE),
                shape = TriangleShape,
                action = { onSelected(it) }
            ))
            diamond.draw(properties = CanvasProperties(
                color = selectionColor(selected == DIAMOND),
                shape = DiamondShape,
                action = { onSelected(it) }
            ))
            triangleDown.draw(properties = CanvasProperties(
                color = selectionColor(selected == TRIANGLE_DOWN),
                shape = TriangleDownShape,
                action = { onSelected(it) }
            ))
        }

        Spacer(Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            plus.draw(properties = CanvasProperties(
                color = selectionColor(selected == PLUS),
                action = { onSelected(it) }
            ))
            button.draw(properties = CanvasProperties(
                color = selectionColor(selected == BUTTON),
                action = { onSelected(it) }
            ))
            snowflake.draw(properties = CanvasProperties(
                color = selectionColor(selected == SNOWFLAKE),
                action = { onSelected(it) }
            ))
            cracker.draw(properties = CanvasProperties(
                color = selectionColor(selected == CRACKER),
                action = { onSelected(it) }
            ))
            star.draw(properties = CanvasProperties(
                color = selectionColor(selected == STAR),
                shape = StarShape,
                action = { onSelected(it) }
            ))
        }

        Spacer(Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            x.draw(properties = CanvasProperties(
                color = selectionColor(selected == X),
                action = { onSelected(it) }
            ))
            heart.draw(properties = CanvasProperties(
                color = selectionColor(selected == HEART),
                shape = HeartShape,
                action = onSelected
            ))
        }
    }
}

@Composable
private fun selectionColor(selected: Boolean): Color =
    if (selected) {
        if (isSystemInDarkTheme()) colorScheme.primary else colorScheme.primaryContainer
    } else {
        if (isSystemInDarkTheme()) colorScheme.primaryContainer else colorScheme.primary
    }