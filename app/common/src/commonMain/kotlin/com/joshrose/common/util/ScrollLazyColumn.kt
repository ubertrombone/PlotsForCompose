package com.joshrose.common.util

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun ScrollLazyColumn(
    modifier: Modifier = Modifier,
    items: LazyListScope.() -> Unit
)