package com.joshrose.common.ui.axes.guidelines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.joshrose.common.ui.axes.DecButton
import com.joshrose.common.ui.axes.IncButton
import com.joshrose.plotsforcompose.axis.util.formatToString

@Composable
fun Alpha(
    alpha: Float,
    incClick: () -> Unit,
    decClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Alpha",
            color = colorScheme.primary,
            fontSize = typography.labelLarge.fontSize
        )

        Spacer(Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IncButton(
                value = alpha,
                limit = .9f,
                contentDescription = "Increase Alpha",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(enabled = alpha < .9f) { incClick() }
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = alpha.formatToString("#.#"),
                color = colorScheme.primary,
                fontSize = typography.labelLarge.fontSize
            )

            Spacer(Modifier.width(10.dp))

            DecButton(
                value = alpha,
                limit = 0.1f,
                contentDescription = "Decrease Alpha",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(enabled = alpha > .1f) { decClick() }
            )
        }
    }
}