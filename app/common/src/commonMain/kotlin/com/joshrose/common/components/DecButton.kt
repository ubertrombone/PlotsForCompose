package com.joshrose.common.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DecButton(
    width: Float,
    limit: Float,
    contentDescription: String?,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val enabled = width > limit
    Icon(
        imageVector = Icons.Outlined.KeyboardArrowDown,
        contentDescription = contentDescription,
        tint = if (enabled) colorScheme.primary else colorScheme.secondary,
        modifier = modifier
            .size(25.dp)
            .clip(CircleShape)
            .background(
                color = if (enabled) colorScheme.primaryContainer else colorScheme.secondaryContainer,
                shape = CircleShape
            )
            .combinedClickable(
                enabled = enabled,
                onClick = onClick,
                onLongClick = onLongClick
            )
    )
}