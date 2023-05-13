package com.joshrose.common.ui.axes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.common.util.ImageResources.DOUBLE_ARROW_UP
import com.joshrose.common.util.createPainter

@Composable
fun RepeatableIncButton(
    enabled: Boolean = true,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = createPainter(DOUBLE_ARROW_UP),
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