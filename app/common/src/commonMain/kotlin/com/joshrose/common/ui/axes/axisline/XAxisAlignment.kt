package com.joshrose.common.ui.axes.axisline

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun XAxisAlignment(
    currentSelected: XAxis?,
    enabled: Boolean = true,
    onClick: (XAxis?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Alignment",
            color = colorScheme.primary,
            fontSize = typography.titleMedium.fontSize
        )

        Spacer(Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            XAlignmentButton(
                selected = currentSelected == null,
                icon = painterResource("auto_awesome.xml"),
                contentDescription = "Automatic Alignment",
                axisPosition = null,
                enabled = enabled,
                onClick = { onClick(it) }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            XAlignmentButton(
                selected = currentSelected == Top,
                icon = painterResource("align_vertical_top.xml"),
                contentDescription = "Align top",
                axisPosition = Top,
                enabled = enabled,
                onClick = { onClick(it) }
            )

            XAlignmentButton(
                selected = currentSelected == Bottom,
                icon = painterResource("align_vertical_bottom.xml"),
                contentDescription = "Align bottom",
                axisPosition = Bottom,
                enabled = enabled,
                onClick = { onClick(it) }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            XAlignmentButton(
                selected = currentSelected == Center,
                icon = painterResource("align_vertical_center.xml"),
                contentDescription = "Align center",
                axisPosition = Center,
                enabled = enabled,
                onClick = { onClick(it) }
            )

            XAlignmentButton(
                selected = currentSelected == Both,
                icon = painterResource("align_vertical_both.xml"),
                contentDescription = "Align Both",
                axisPosition = Both,
                enabled = enabled,
                onClick = { onClick(it) }
            )
        }
    }
}