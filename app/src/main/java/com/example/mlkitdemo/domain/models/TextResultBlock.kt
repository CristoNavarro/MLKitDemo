package com.example.mlkitdemo.domain.models

import androidx.compose.ui.geometry.Rect

data class TextResultBlock(
    val text: String,
    val boundingBox: Rect,
    val lines: List<TextResultLine>
)