package com.joshrose.common.ui.axes.visibility

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joshrose.common.ui.axes.ElevatedFilterChip

@Composable
fun VisibilityCard(
    label: String,
    axisSelected: Boolean,
    axisLineSelected: Boolean,
    guidelinesSelected: Boolean,
    labelsSelected: Boolean,
    axisOnClick: () -> Unit,
    axisLineOnClick: () -> Unit,
    guidelinesOnClick: () -> Unit,
    labelsOnClick: () -> Unit,
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
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(label, color = colorScheme.primary, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(10.dp))

            ElevatedFilterChip(selected = axisSelected, label = "Axis") { axisOnClick() }

            Spacer(Modifier.height(10.dp))

            ElevatedFilterChip(selected = axisLineSelected, label = "Axis Line") { axisLineOnClick() }

            Spacer(Modifier.height(10.dp))

            ElevatedFilterChip(selected = guidelinesSelected, label = "Guidelines") { guidelinesOnClick() }

            Spacer(Modifier.height(10.dp))

            ElevatedFilterChip(selected = labelsSelected, label = "Labels") { labelsOnClick() }

            Spacer(Modifier.height(10.dp))
        }
    }
}