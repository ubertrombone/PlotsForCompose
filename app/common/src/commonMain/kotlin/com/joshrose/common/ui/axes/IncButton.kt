@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.joshrose.common.ui.axes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IncButton(
    value: Float,
    limit: Float,
    contentDescription: String?,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val enabled = value < limit
    Icon(
        imageVector = Icons.Outlined.KeyboardArrowUp,
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