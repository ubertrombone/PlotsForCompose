package com.joshrose.common.ui.linegraph.label_marker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.linegraph.util.Style
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StyleToggle(
    selected: Style,
    onSelectChange: (Style) -> Unit,
    modifier: Modifier = Modifier
) {
    val styles = listOf("Auto", "Fill", "Stroke")

    Surface(
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 4.dp,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(24.dp))
                .background(colorScheme.inversePrimary)
        ) {
            styles.forEach { style ->
                when (style) {
                    "Auto" -> {
                        val styl = Style.Auto
                        Icon(
                            painter = painterResource("auto_awesome.xml"),
                            contentDescription = "Auto select label marker style.",
                            tint = colorScheme.primary,
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(24.dp))
                                .clickable { onSelectChange(styl) }
                                .background(if (styl == selected) colorScheme.primaryContainer else colorScheme.inversePrimary)
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                        )
                    }
                    "Fill" -> {
                        val styl = Style.Fill
                        Icon(
                            painter = painterResource("stroke_full.xml"),
                            contentDescription = "Select Fill for label marker style.",
                            tint = colorScheme.primary,
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(24.dp))
                                .clickable { onSelectChange(styl) }
                                .background(if (styl == selected) colorScheme.primaryContainer else colorScheme.inversePrimary)
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                        )
                    }
                    "Stroke" -> {
                        val styl = Style.Stroke(strokeWidth = 2f)
                        Icon(
                            painter = painterResource("circle.xml"),
                            contentDescription = "Select Stroke for label marker style.",
                            tint = colorScheme.primary,
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(24.dp))
                                .clickable { onSelectChange(styl) }
                                .background(if (styl == selected) colorScheme.primaryContainer else colorScheme.inversePrimary)
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}