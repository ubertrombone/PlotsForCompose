package com.joshrose.common.ui.linegraph.label_marker

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
import com.joshrose.common.components.linegraph.label_marker.LabelMarkerComponent

@Composable
fun LabelMarkerContent(
    component: LabelMarkerComponent,
    markerSize: Float,
    modifier: Modifier = Modifier
) {
    val labelMarker by component.labelMarkerStates.subscribeAsState()

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

            LabelMarkerRadiusContent(
                selected = labelMarker.radius,
                onSelectChange = component::updateRadius,
                markerSize = markerSize
            )

            Spacer(Modifier.height(10.dp))

            LabelMarkerStyleContent(
                selected = labelMarker.style,
                onSelectChange = component::updateStyle
            )
        }
    }
}