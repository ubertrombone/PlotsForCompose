package com.joshrose.common.ui.linegraph.line

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
import com.joshrose.common.components.linegraph.line.LineComponent
import com.joshrose.common.util.ShortClickButton

@Composable
fun LineContent(component: LineComponent, modifier: Modifier) {
    val line by component.lineStates.subscribeAsState()

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
            Text("Line Options", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(10.dp))

            LineTypeContent(
                currentSelected = line.lineType,
                onSelected = component::updateType
            )

            Spacer(Modifier.height(10.dp))

            LineJoinContent(
                currentSelected = line.strokeJoin,
                onSelected = component::updateJoin
            )

            Spacer(Modifier.height(10.dp))

            ShortClickButton(
                label = "Stroke Width",
                value = line.strokeWidth,
                upperLimit = 10f,
                lowerLimit = 1f,
                incClick = component::incStrokeWidth,
                decClick = component::decStrokeWidth,
                horizontalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterHorizontally)
            )
        }
    }
}