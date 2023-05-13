package com.joshrose.common.ui.axes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IncButton(
    enabled: Boolean = true,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Outlined.KeyboardArrowUp,
        contentDescription = contentDescription,
        tint = if (enabled) colorScheme.primary else colorScheme.secondary,
        modifier = modifier
            .size(28.dp)
            .background(
                color = if (enabled) colorScheme.primaryContainer else colorScheme.secondaryContainer,
                shape = CircleShape
            )
    )
}