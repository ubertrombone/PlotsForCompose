package com.joshrose.common.ui.axes

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
import androidx.compose.ui.unit.dp

@Composable
fun StrokeWidth(
    width: Float,
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
            text = "Stroke Width",
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
                value = width,
                limit = 5f,
                contentDescription = "Increase Stroke Width",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(enabled = width < 5f) { incClick() }
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = width.toInt().toString(),
                color = colorScheme.primary,
                fontSize = typography.labelLarge.fontSize
            )

            Spacer(Modifier.width(10.dp))

            DecButton(
                value = width,
                limit = 1f,
                contentDescription = "Decrease Stroke Width",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(enabled = width > 1f) { decClick() }
            )
        }
    }
}