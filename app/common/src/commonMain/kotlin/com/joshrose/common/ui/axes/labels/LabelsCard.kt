package com.joshrose.common.ui.axes.labels

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joshrose.common.util.RepeatableButton
import com.joshrose.common.util.ShortClickButton
import com.joshrose.plotsforcompose.axis.config.util.Multiplier

@Composable
fun LabelsCard(
    label: String,
    rotation: Float,
    axisOffset: Float,
    breaks: Int,
    rangeAdjustment: Multiplier,
    maxAdjustment: Multiplier,
    minAdjustment: Multiplier,
    incRotationClick: () -> Unit,
    decRotationClick: () -> Unit,
    incAxisOffsetClick: () -> Unit,
    decAxisOffsetClick: () -> Unit,
    incBreaksClick: () -> Unit,
    decBreaksClick: () -> Unit,
    incRangeAdjClick: () -> Unit,
    decRangeAdjClick: () -> Unit,
    incMaxAdjClick: () -> Unit,
    decMaxAdjClick: () -> Unit,
    incMinAdjClick: () -> Unit,
    decMinAdjClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primaryContainer),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(label, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(10.dp))

            RepeatableButton(
                label = "Rotation",
                value = rotation,
                upperLimit = 90f,
                lowerLimit = -90f,
                incClick = incRotationClick,
                decClick = decRotationClick
            )

            Spacer(Modifier.height(10.dp))

            RepeatableButton(
                label = "Axis Offset",
                value = axisOffset,
                upperLimit = 50f,
                lowerLimit = -50f,
                incClick = incAxisOffsetClick,
                decClick = decAxisOffsetClick
            )

            Spacer(Modifier.height(10.dp))

            ShortClickButton(
                label = "Breaks",
                value = breaks,
                upperLimit = 10,
                lowerLimit = 2,
                incClick = incBreaksClick,
                decClick = decBreaksClick
            )

            Spacer(Modifier.height(10.dp))

            ShortClickButton(
                label = "Range Adj",
                value = rangeAdjustment,
                upperLimit = .9f,
                lowerLimit = .1f,
                incClick = incRangeAdjClick,
                decClick = decRangeAdjClick
            )

            Spacer(Modifier.height(10.dp))

            ShortClickButton(
                label = "Max Adj",
                value = maxAdjustment,
                upperLimit = .9f,
                lowerLimit = .1f,
                incClick = incMaxAdjClick,
                decClick = decMaxAdjClick
            )

            Spacer(Modifier.height(10.dp))

            ShortClickButton(
                label = "Min Adj",
                value = minAdjustment,
                upperLimit = .9f,
                lowerLimit = .1f,
                incClick = incMinAdjClick,
                decClick = decMinAdjClick
            )
        }
    }
}