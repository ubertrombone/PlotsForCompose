package com.joshrose.common.ui.axes.axisline

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joshrose.common.util.ShortClickButton
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Companion.Orientation

@Suppress("DuplicatedCode")
@Composable
fun AxisLineCard(
    label: String,
    alpha: Multiplier,
    strokeWidth: Float,
    checked: Boolean,
    orientation: Orientation,
    axisPosition: AxisPosition?,
    enabled: Boolean = true,
    incAlphaClick: () -> Unit,
    decAlphaClick: () -> Unit,
    incStrokeWidthClick: () -> Unit,
    decStrokeWidthClick: () -> Unit,
    onCheckClick: (Boolean) -> Unit,
    onAlignmentClick: (AxisPosition?) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, color = colorScheme.primaryContainer),
        colors = CardDefaults.cardColors(
            contentColor = colorScheme.primary,
            containerColor = colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(label, color = colorScheme.primary, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(10.dp))

            ShortClickButton(
                label = "Stroke Width",
                value = strokeWidth,
                upperLimit = 5f,
                lowerLimit = 1f,
                incClick = incStrokeWidthClick,
                decClick = decStrokeWidthClick,
                enabled = enabled
            )

            Spacer(Modifier.height(10.dp))

            ShortClickButton(
                label = "Alpha",
                value = alpha,
                upperLimit = 1f,
                lowerLimit = 0f,
                incClick = incAlphaClick,
                decClick = decAlphaClick,
                enabled = enabled
            )

            Spacer(Modifier.height(10.dp))

            Ticks(
                checked = checked,
                onClick = onCheckClick,
                enabled = enabled
            )

            Spacer(Modifier.height(10.dp))

            when (orientation) {
                Orientation.X -> XAxisAlignment(
                    currentSelected = axisPosition,
                    enabled = enabled,
                    onClick = { onAlignmentClick(it) }
                )
                Orientation.Y -> YAxisAlignment(
                    currentSelected = axisPosition,
                    enabled = enabled,
                    onClick = { onAlignmentClick(it) }
                )
            }
        }
    }
}