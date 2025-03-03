package com.example.mlkitdemo.data.mappers

import com.example.mlkitdemo.domain.models.ImageLabelResult
import com.example.mlkitdemo.domain.models.ObjectDetectionResult
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.DetectedObject.Label

fun List<DetectedObject>.toObjectDetectionResult(): List<ObjectDetectionResult> {
    return map { it.toObjectDetectionResult() }
}

fun DetectedObject.toObjectDetectionResult(): ObjectDetectionResult {
    return ObjectDetectionResult(
        boundingBox = boundingBox,
        labels = labels.toImageLabelResult()
    )
}

fun List<Label>.toImageLabelResult(): List<ImageLabelResult> {
    return map { ImageLabelResult(
        text = it.text,
        confidence = it.confidence
    ) }
}