package com.joshrose.common.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun ScrollLazyColumn(
    modifier: Modifier,
    items: LazyListScope.() -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) { items() }
}