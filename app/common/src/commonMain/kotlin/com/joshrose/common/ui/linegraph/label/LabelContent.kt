package com.joshrose.common.ui.linegraph.label

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
import com.joshrose.common.components.linegraph.label.LabelComponent
import com.joshrose.common.util.RepeatableButton
import com.joshrose.common.util.ShortClickButton

@Composable
fun LabelContent(component: LabelComponent, modifier: Modifier = Modifier) {
    val label by component.labelStates.subscribeAsState()

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
            Text("Label Options", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(10.dp))

            RepeatableButton(
                label = "Font Size",
                value = label.fontSize,
                upperLimit = 25f,
                lowerLimit = 1f,
                incClick = component::incFontSize,
                decClick = component::decFontSize,
                horizontalArrangement = Arrangement.SpaceEvenly
            )

            Spacer(Modifier.height(10.dp))

            ShortClickButton(
                label = "Box Alpha",
                value = label.boxAlpha,
                upperLimit = 1f,
                lowerLimit = 0f,
                incClick = component::incBoxAlpha,
                decClick = component::decBoxAlpha,
                pattern = "#.#",
                horizontalArrangement = Arrangement.SpaceEvenly
            )

            Spacer(Modifier.height(10.dp))

            RepeatableButton(
                label = "X Corner Radius",
                value = label.xCornerRadius,
                upperLimit = 50f,
                lowerLimit = 1f,
                incClick = component::incXCornerRadius,
                decClick = component::decXCornerRadius,
                horizontalArrangement = Arrangement.SpaceEvenly
            )

            Spacer(Modifier.height(10.dp))

            RepeatableButton(
                label = "Y Corner Radius",
                value = label.yCornerRadius,
                upperLimit = 50f,
                lowerLimit = 1f,
                incClick = component::incYCornerRadius,
                decClick = component::decYCornerRadius,
                horizontalArrangement = Arrangement.SpaceEvenly
            )
        }
    }
}