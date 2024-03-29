package com.joshrose.common.ui.linegraph.label_line

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.linegraph.label_line.LabelLineComponent
import com.joshrose.common.util.ShortClickButton

@Composable
fun LabelLineContent(component: LabelLineComponent, modifier: Modifier = Modifier) {
    val labelLine by component.labelLineStates.subscribeAsState()

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primaryContainer),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Label Line Options", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(10.dp))

            ShortClickButton(
                label = "Alpha",
                value = labelLine.alpha,
                upperLimit = 1f,
                lowerLimit = 0f,
                incClick = component::incAlpha,
                decClick = component::decAlpha,
                pattern = "#.#",
                horizontalArrangement = Arrangement.SpaceEvenly
            )

            Spacer(Modifier.height(10.dp))

            ShortClickButton(
                label = "Stroke Width",
                value = labelLine.strokeWidth,
                upperLimit = 10f,
                lowerLimit = 1f,
                incClick = component::incStrokeWidth,
                decClick = component::decStrokeWidth,
                horizontalArrangement = Arrangement.SpaceEvenly
            )

            Spacer(Modifier.height(10.dp))

            LabelLineCapContent(
                selected = labelLine.strokeCap,
                onSelectChange = component::updateStrokeCap
            )
        }
    }
}