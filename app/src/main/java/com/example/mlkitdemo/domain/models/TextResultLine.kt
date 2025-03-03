package com.example.mlkitdemo.domain.models

import androidx.compose.ui.geometry.Rect

data class TextResultLine(
    val text: String,
    val boundingBox: Rect,
)