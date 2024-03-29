package com.joshrose.common.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.util.Proportional
import java.text.DecimalFormat

@Composable
fun ShortClickButton(
    label: String,
    value: Float,
    upperLimit: Float,
    lowerLimit: Float,
    incClick: () -> Unit,
    decClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    pattern: String = "#"
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = colorScheme.primary,
            fontSize = typography.titleMedium.fontSize,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val incEnabled = value < upperLimit && enabled
            IncButton(
                enabled = incEnabled,
                contentDescription = "Increase $label",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(enabled = incEnabled, onClick = incClick)
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = DecimalFormat(pattern).format(value).toString(),
                color = colorScheme.primary,
                fontSize = typography.labelLarge.fontSize
            )

            Spacer(Modifier.width(10.dp))

            val decEnabled = value > lowerLimit && enabled
            DecButton(
                enabled = decEnabled,
                contentDescription = "Decrease $label",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(enabled = decEnabled, onClick = decClick)
            )
        }
    }
}

@Composable
fun ShortClickButton(
    label: String,
    value: Int,
    upperLimit: Int,
    lowerLimit: Int,
    incClick: () -> Unit,
    decClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = colorScheme.primary,
            fontSize = typography.titleMedium.fontSize,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val incEnabled = value < upperLimit && enabled
            IncButton(
                enabled = incEnabled,
                contentDescription = "Increase $label",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(enabled = incEnabled, onClick = incClick)
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = value.toString(),
                color = colorScheme.primary,
                fontSize = typography.labelLarge.fontSize
            )

            Spacer(Modifier.width(10.dp))

            val decEnabled = value > lowerLimit && enabled
            DecButton(
                enabled = decEnabled,
                contentDescription = "Decrease $label",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(enabled = decEnabled, onClick = decClick)
            )
        }
    }
}

@Composable
fun ShortClickButton(
    label: String,
    value: Multiplier,
    upperLimit: Float,
    lowerLimit: Float,
    incClick: () -> Unit,
    decClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = colorScheme.primary,
            fontSize = typography.titleMedium.fontSize,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val incEnabled = value.factor < upperLimit && enabled
            IncButton(
                enabled = incEnabled,
                contentDescription = "Increase $label",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(enabled = incEnabled, onClick = incClick)
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = DecimalFormat("#.#").format(value.factor).toString(),
                color = colorScheme.primary,
                fontSize = typography.labelLarge.fontSize
            )

            Spacer(Modifier.width(10.dp))

            val decEnabled = value.factor > lowerLimit && enabled
            DecButton(
                enabled = decEnabled,
                contentDescription = "Decrease $label",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(enabled = decEnabled, onClick = decClick)
            )
        }
    }
}

@Composable
fun ShortClickButton(
    label: String,
    value: Proportional?,
    upperLimit: Float,
    lowerLimit: Float,
    incClick: () -> Unit,
    decClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = colorScheme.primary,
            fontSize = typography.titleMedium.fontSize,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val incEnabled = ((value?.factor ?: 0f) < upperLimit) && enabled
            IncButton(
                enabled = incEnabled,
                contentDescription = "Increase $label",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(enabled = incEnabled, onClick = incClick)
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = DecimalFormat("#.##").format(value?.factor ?: 0).toString(),
                color = colorScheme.primary,
                fontSize = typography.labelLarge.fontSize
            )

            Spacer(Modifier.width(10.dp))

            val decEnabled = ((value?.factor ?: 0f) > lowerLimit) && enabled
            DecButton(
                enabled = decEnabled,
                contentDescription = "Decrease $label",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(enabled = decEnabled, onClick = decClick)
            )
        }
    }
}