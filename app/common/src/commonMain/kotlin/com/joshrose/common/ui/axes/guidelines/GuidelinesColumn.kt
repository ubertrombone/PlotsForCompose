package com.joshrose.common.ui.axes.guidelines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GuidelinesColumn(
    label: String,
    strokeWidth: Float,
    incStrokeWidthClick: () -> Unit,
    decStrokeWidthClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(label, color = MaterialTheme.colorScheme.primary)

        Spacer(Modifier.height(10.dp))

        StrokeWidth(
            width = strokeWidth,
            incClick = incStrokeWidthClick,
            decClick = decStrokeWidthClick
        )
        // TODO: Alpha
        // TODO: Padding
    }
}