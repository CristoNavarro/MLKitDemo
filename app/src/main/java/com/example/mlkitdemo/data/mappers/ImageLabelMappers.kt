package com.example.mlkitdemo.data.mappers

import com.example.mlkitdemo.domain.models.ImageLabelResult
import com.google.mlkit.vision.label.ImageLabel

fun List<ImageLabel>.toImageLabelResult(): List<ImageLabelResult> {
    return map {
        ImageLabelResult(it.text, it.confidence)
    }
}