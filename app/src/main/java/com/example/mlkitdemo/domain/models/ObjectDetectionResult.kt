package com.example.mlkitdemo.domain.models

import android.graphics.Rect

data class ObjectDetectionResult(
    val boundingBox: Rect,
    val labels: List<ImageLabelResult>
)
