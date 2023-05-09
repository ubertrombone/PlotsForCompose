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
import com.joshrose.common.ui.axes.Alpha
import com.joshrose.common.ui.axes.StrokeWidth
import com.joshrose.plotsforcompose.axis.config.util.Multiplier

@Suppress("DuplicatedCode")
@Composable
fun AxisLineCard(
    label: String,
    alpha: Multiplier,
    strokeWidth: Float,
    checked: Boolean,
    incAlphaClick: () -> Unit,
    decAlphaClick: () -> Unit,
    incStrokeWidthClick: () -> Unit,
    decStrokeWidthClick: () -> Unit,
    onCheckClick: (Boolean) -> Unit,
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
        // TODO: Figure out how to do Axis Position
        // TODO: Change Switch colors
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(label, color = colorScheme.primary, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(10.dp))

            StrokeWidth(
                width = strokeWidth,
                incClick = incStrokeWidthClick,
                decClick = decStrokeWidthClick
            )

            Spacer(Modifier.height(10.dp))

            Alpha(
                alpha = alpha,
                incClick = incAlphaClick,
                decClick = decAlphaClick
            )

            Spacer(Modifier.height(10.dp))

            Ticks(
                checked = checked,
                onClick = onCheckClick
            )
        }
    }
}