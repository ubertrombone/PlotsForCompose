package com.joshrose.common.util

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.joshrose.common.ui.axes.DecButton
import com.joshrose.common.ui.axes.IncButton

@Composable
fun RepeatableButton(
    label: String,
    value: Float,
    upperLimit: Float,
    lowerLimit: Float,
    incClick: () -> Unit,
    decClick: () -> Unit,
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
        )

        Spacer(Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IncButton(
                value = value,
                limit = upperLimit,
                contentDescription = "Increase $label",
                modifier = Modifier
                    .clip(CircleShape)
                    .repeatingClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        enabled = value < upperLimit,
                        onClick = incClick
                    )
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = value.toInt().toString(),
                color = colorScheme.primary,
                fontSize = typography.labelLarge.fontSize
            )

            Spacer(Modifier.width(10.dp))

            DecButton(
                value = value,
                limit = lowerLimit,
                contentDescription = "Decrease $label",
                modifier = Modifier
                    .clip(CircleShape)
                    .repeatingClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        enabled = value > lowerLimit,
                        onClick = decClick
                    )
            )
        }
    }
}