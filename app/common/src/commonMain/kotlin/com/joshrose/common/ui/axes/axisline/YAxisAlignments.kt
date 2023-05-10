package com.joshrose.common.ui.axes.axisline

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.common.util.ImageResources.*
import com.joshrose.common.util.createPainter
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.YAxisPosition.*

@Composable
fun YAxisAlignment(
    currentSelected: AxisPosition?,
    onClick: (AxisPosition?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Alignment",
            color = colorScheme.primary,
            fontSize = typography.titleMedium.fontSize
        )

        Spacer(Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AlignmentButton(
                selected = currentSelected == null,
                icon = createPainter(file = ALIGN_AUTO),
                contentDescription = "Automatic Alignment",
                axisPosition = null,
                onClick = { onClick(it) }
            )

            AlignmentButton(
                selected = currentSelected == CENTER,
                icon = createPainter(file = ALIGN_CENTER_VERTICAL),
                contentDescription = "Align center",
                axisPosition = CENTER,
                onClick = { onClick(it) }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AlignmentButton(
                selected = currentSelected == START,
                icon = createPainter(file = ALIGN_LEFT),
                contentDescription = "Align start",
                axisPosition = START,
                onClick = { onClick(it) }
            )

            AlignmentButton(
                selected = currentSelected == END,
                icon = createPainter(file = ALIGN_RIGHT),
                contentDescription = "Align end",
                axisPosition = END,
                onClick = { onClick(it) }
            )
        }
    }
}