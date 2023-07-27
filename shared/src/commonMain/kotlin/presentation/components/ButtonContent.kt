package com.reportvox.reportvoxcontributor.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonContent(
    content: @Composable () -> Unit,
) {
    Box(modifier = Modifier.padding(vertical = 4.dp)) {
        content()
    }
}