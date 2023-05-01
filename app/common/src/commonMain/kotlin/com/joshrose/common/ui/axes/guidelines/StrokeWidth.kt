package com.joshrose.common.ui.axes.guidelines

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Stroke Width",
            color = colorScheme.primary,
            fontSize = typography.labelLarge.fontSize
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalIconButton(
                enabled = width < 5f,
                onClick = incClick,
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = colorScheme.primaryContainer,
                    contentColor = colorScheme.primary,
                    disabledContainerColor = colorScheme.secondaryContainer,
                    disabledContentColor = colorScheme.onSecondaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Increase Stroke Width"
                )
            }

            Spacer(Modifier.width(10.dp))

            Text(
                text = width.toInt().toString(),
                color = colorScheme.primary,
                fontSize = typography.labelLarge.fontSize
            )

            Spacer(Modifier.width(10.dp))

            FilledTonalIconButton(
                enabled = width > 1f,
                onClick = decClick,
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = colorScheme.primaryContainer,
                    contentColor = colorScheme.primary,
                    disabledContainerColor = colorScheme.secondaryContainer,
                    disabledContentColor = colorScheme.onSecondaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Decrease Stroke Width"
                )
            }
        }
    }
}