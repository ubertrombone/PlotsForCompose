package com.joshrose.common.ui.axes.guidelines

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
import com.joshrose.common.util.RepeatableButton
import com.joshrose.common.util.ShortClickButton
import com.joshrose.plotsforcompose.axis.config.util.Multiplier

@Suppress("DuplicatedCode")
@Composable
fun GuidelinesCard(
    label: String,
    alpha: Multiplier,
    strokeWidth: Float,
    padding: Float,
    enabled: Boolean = true,
    incAlphaClick: () -> Unit,
    decAlphaClick: () -> Unit,
    incStrokeWidthClick: () -> Unit,
    decStrokeWidthClick: () -> Unit,
    incPaddingClick: () -> Unit,
    decPaddingClick: () -> Unit,
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

            RepeatableButton(
                label = "Padding",
                value = padding,
                upperLimit = 100f,
                lowerLimit = 0f,
                incClick = incPaddingClick,
                decClick = decPaddingClick,
                enabled = enabled
            )
        }
    }
}