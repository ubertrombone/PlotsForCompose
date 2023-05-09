package com.joshrose.common.ui.axes.axisline

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Ticks(
    checked: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Ticks",
            color = colorScheme.primary,
            fontSize = typography.titleMedium.fontSize
        )

        Spacer(Modifier.width(10.dp))

        Switch(
            checked = checked,
            onCheckedChange = onClick,
            thumbContent = if (checked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else null,
            colors = SwitchDefaults.colors(
                checkedThumbColor = colorScheme.primaryContainer,
                checkedTrackColor = colorScheme.tertiary,
                checkedIconColor = colorScheme.onPrimaryContainer,
                checkedBorderColor = colorScheme.background,
                uncheckedThumbColor = colorScheme.secondaryContainer,
                uncheckedTrackColor = colorScheme.tertiary,
                uncheckedIconColor = colorScheme.onSecondaryContainer,
                uncheckedBorderColor = colorScheme.background
            )
        )
    }
}