package com.joshrose.common.ui.axes.guidelines

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.common.ui.axes.DecButton
import com.joshrose.common.ui.axes.IncButton
import com.joshrose.common.util.repeatingClickable
import com.joshrose.plotsforcompose.axis.util.formatToString

@Composable
fun Padding(
    padding: Float,
    incClick: () -> Unit,
    decClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Padding",
            color = colorScheme.primary,
            fontSize = typography.labelLarge.fontSize
        )

        Spacer(Modifier.height(5.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IncButton(
                value = padding,
                limit = 100f,
                contentDescription = "Increase Padding",
                onClick = incClick
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = padding.formatToString("#"),
                color = colorScheme.primary,
                fontSize = typography.labelLarge.fontSize
            )

            Spacer(Modifier.width(10.dp))

            DecButton(
                value = padding,
                limit = 0f,
                contentDescription = "Decrease Padding",
                onClick = decClick,
                modifier = Modifier.repeatingClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    enabled = padding > 0f,
                    onClick = decClick
                )
            )
        }
    }
}