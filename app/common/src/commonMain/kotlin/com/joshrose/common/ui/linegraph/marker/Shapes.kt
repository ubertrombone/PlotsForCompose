package com.joshrose.common.ui.linegraph.marker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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

        Spacer(Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Circle(
                color = selectionColor(selected == CIRCLE),
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .clickable { onSelected(CIRCLE) }
            )
            Square(
                color = selectionColor(selected == SQUARE),
                modifier = Modifier
                    .size(28.dp)
                    .clip(RectangleShape)
                    .clickable { onSelected(SQUARE) }
            )
            Triangle(
                color = selectionColor(selected == TRIANGLE),
                modifier = Modifier
                    .size(28.dp)
                    .clip(TriangleShape)
                    .clickable { onSelected(TRIANGLE) }
            )
            Diamond(
                color = selectionColor(selected == DIAMOND),
                modifier = Modifier
                    .size(28.dp)
                    .clip(DiamondShape)
                    .clickable { onSelected(DIAMOND) }
            )
            TriangleDown(
                color = selectionColor(selected == TRIANGLE_DOWN),
                modifier = Modifier
                    .size(28.dp)
                    .clip(TriangleDownShape)
                    .clickable { onSelected(TRIANGLE_DOWN) }
            )
        }

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Plus(
                color = selectionColor(selected == PLUS),
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onSelected(PLUS) }
            )
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