package com.example.mlkitdemo.presentation.screens.objectDetection.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.unit.dp
import com.example.mlkitdemo.domain.models.ObjectDetectionResult

@Composable
fun CameraOverlay(
    results: List<ObjectDetectionResult>,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawWithContent {
                drawContent()
                results.forEach { result ->
                    drawRect(
                        topLeft = result.boundingBox.toComposeRect().topLeft,
                        size = result.boundingBox.toComposeRect().size,
                        color = Color.Red,
                        style = Stroke(2.dp.toPx())
                    )
                }
            }
    ) {
        content()
    }
}