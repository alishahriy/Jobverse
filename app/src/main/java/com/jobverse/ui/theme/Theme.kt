package com.jobverse.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val JobverseColors = darkColors(
    primary = androidx.compose.ui.graphics.Color(0xFF1E88E5),
    secondary = androidx.compose.ui.graphics.Color(0xFF43A047)
)

@Composable
fun JobverseTheme(content:@Composable ()->Unit) {
    MaterialTheme(colors = JobverseColors) {
        content()
    }
}
