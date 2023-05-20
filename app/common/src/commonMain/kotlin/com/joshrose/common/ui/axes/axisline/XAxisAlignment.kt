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
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*

@Composable
fun XAxisAlignment(
    currentSelected: XAxis?,
    enabled: Boolean = true,
    onClick: (XAxis?) -> Unit,
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
            XAlignmentButton(
                selected = currentSelected == null,
                icon = createPainter(file = ALIGN_AUTO),
                contentDescription = "Automatic Alignment",
                axisPosition = null,
                enabled = enabled,
                onClick = { onClick(it) }
            )

            XAlignmentButton(
                selected = currentSelected == Center,
                icon = createPainter(file = ALIGN_CENTER_VERTICAL),
                contentDescription = "Align center",
                axisPosition = Center,
                enabled = enabled,
                onClick = { onClick(it) }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            XAlignmentButton(
                selected = currentSelected == Top,
                icon = createPainter(file = ALIGN_TOP),
                contentDescription = "Align top",
                axisPosition = Top,
                enabled = enabled,
                onClick = { onClick(it) }
            )

            XAlignmentButton(
                selected = currentSelected == Bottom,
                icon = createPainter(file = ALIGN_BOTTOM),
                contentDescription = "Align bottom",
                axisPosition = Bottom,
                enabled = enabled,
                onClick = { onClick(it) }
            )
        }
    }
}