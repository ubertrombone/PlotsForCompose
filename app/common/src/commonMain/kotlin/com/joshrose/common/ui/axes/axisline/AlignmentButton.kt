package com.joshrose.common.ui.axes.axisline

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
    IconButton(
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = if (selected) colorScheme.primary else colorScheme.secondary
        ),
        onClick = { onClick(axisPosition) }
    ) {
        Icon(painter = icon, contentDescription = contentDescription)
    }
}