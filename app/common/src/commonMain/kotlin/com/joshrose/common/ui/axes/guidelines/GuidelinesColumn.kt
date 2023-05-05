package com.joshrose.common.ui.axes.guidelines

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GuidelinesColumn(
    label: String,
    alpha: Float,
    strokeWidth: Float,
    padding: Float,
    incAlphaClick: () -> Unit,
    decAlphaClick: () -> Unit,
    incStrokeWidthClick: () -> Unit,
    decStrokeWidthClick: () -> Unit,
    incPaddingClick: () -> Unit,
    decPaddingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, color = colorScheme.primaryContainer),
        colors = CardDefaults.cardColors(
            contentColor = colorScheme.primary,
            containerColor = colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(label, color = colorScheme.primary)

            Spacer(Modifier.height(10.dp))

            StrokeWidth(
                width = strokeWidth,
                incClick = incStrokeWidthClick,
                decClick = decStrokeWidthClick
            )

            Spacer(Modifier.height(10.dp))

            Alpha(
                alpha = alpha,
                incClick = incAlphaClick,
                decClick = decAlphaClick
            )

            Spacer(Modifier.height(10.dp))

            Padding(
                padding = padding,
                incClick = incPaddingClick,
                decClick = decPaddingClick
            )
        }
    }
}