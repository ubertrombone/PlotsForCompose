package com.joshrose.common.ui.linegraph.line

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun JoinToggle(
    selected: Join,
    onSelectChange: (Join) -> Unit,
    modifier: Modifier = Modifier
) {
    val joins = Join.entries

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
            joins.forEach { join ->
                Text(
                    text = join.joinName(),
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = colorScheme.primary,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(24.dp))
                        .clickable { onSelectChange(join) }
                        .background(if (join == selected) colorScheme.primaryContainer else colorScheme.inversePrimary)
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                )
            }
        }
    }
}