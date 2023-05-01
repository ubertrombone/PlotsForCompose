package com.joshrose.common.ui.axes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.common.util.ScrollLazyRow

@Composable
fun AxisChipRow(
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
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(10.dp))
        ScrollLazyRow(modifier = Modifier.fillMaxWidth()) {
            item {
                ElevatedFilterChip(selected = axisSelected, label = "Axis") { axisOnClick() }
                Spacer(Modifier.width(5.dp))
            }
            item {
                ElevatedFilterChip(selected = axisLineSelected, label = "Axis Line") { axisLineOnClick() }
                Spacer(Modifier.width(5.dp))
            }
            item {
                ElevatedFilterChip(selected = guidelinesSelected, label = "Guidelines") { guidelinesOnClick() }
                Spacer(Modifier.width(5.dp))
            }
            item {
                ElevatedFilterChip(selected = labelsSelected, label = "Labels") { labelsOnClick() }
                Spacer(Modifier.width(5.dp))
            }
        }
    }
}