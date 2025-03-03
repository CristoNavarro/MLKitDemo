package com.example.mlkitdemo.data.mappers

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.toComposeRect
import com.example.mlkitdemo.domain.models.TextResult
import com.example.mlkitdemo.domain.models.TextResultBlock
import com.example.mlkitdemo.domain.models.TextResultLine
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.Text.Line
import com.google.mlkit.vision.text.Text.TextBlock

fun Text.toTextResult(): TextResult {
    return TextResult(
        text = text,
        blocks = textBlocks.map { it.toTextResultBlock() }
    )
}

fun TextBlock.toTextResultBlock(): TextResultBlock {
    return TextResultBlock(
        text = text,
        boundingBox = boundingBox?.toComposeRect() ?: Rect(0f, 0f, 0f, 0f),
        lines = lines.map { it.toTextResultLine() }
    )
}

fun Line.toTextResultLine(): TextResultLine {
    return TextResultLine(
        text = text,
        boundingBox = boundingBox?.toComposeRect() ?: Rect(0f, 0f, 0f, 0f)
    )
}