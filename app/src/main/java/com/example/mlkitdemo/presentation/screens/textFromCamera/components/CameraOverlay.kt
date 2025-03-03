package com.example.mlkitdemo.presentation.screens.textFromCamera.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.mlkitdemo.domain.models.TextResultBlock

@Composable
fun CameraOverlay(
    textBlocks: List<TextResultBlock>,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawWithContent {
                drawContent()
                textBlocks.forEach { block ->
                    drawRect(
                        topLeft = block.boundingBox.topLeft,
                        size = block.boundingBox.size,
                        color = Color.Red,
                        style = Stroke(2.dp.toPx())
                    )
                    block.lines.forEach { line ->
                        drawRect(
                            topLeft = line.boundingBox.topLeft,
                            size = line.boundingBox.size,
                            color = Color.Blue,
                            style = Stroke(1.dp.toPx())
                        )
                    }
                }
            }
    ) {
        content()
    }
}