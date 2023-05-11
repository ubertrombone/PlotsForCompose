package com.joshrose.common.ui.axes.axisline

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.joshrose.plotsforcompose.axis.util.AxisPosition

@Composable
fun AlignmentButton(
    selected: Boolean,
    icon: Painter,
    contentDescription: String?,
    axisPosition: AxisPosition?,
    modifier: Modifier = Modifier,
    onClick: (AxisPosition?) -> Unit,
) {
    val contentColor = if (selected) {
        if (isSystemInDarkTheme()) colorScheme.primary else colorScheme.primaryContainer
    } else {
        if (isSystemInDarkTheme()) colorScheme.primaryContainer else colorScheme.primary
    }
    IconButton(
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(contentColor = contentColor),
        onClick = { onClick(axisPosition) }
    ) {
        Icon(painter = icon, contentDescription = contentDescription)
    }
}