package com.joshrose.common.util

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
actual fun ScrollLazyColumn(
    modifier: Modifier,
    items: LazyListScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        val state = rememberLazyListState()

        LazyColumn(modifier.fillMaxSize(), state) { items() }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState = state),
            style = defaultScrollbarStyle().copy(
                unhoverColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f),
                hoverColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        )
    }
}