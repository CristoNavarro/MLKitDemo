package com.example.mlkitdemo.domain.imageAnalyzer

import androidx.camera.core.ImageAnalysis.Analyzer
import com.example.mlkitdemo.domain.models.ObjectDetectionResult
import kotlinx.coroutines.flow.SharedFlow

interface ObjectDetectionImageAnalyzer: Analyzer {
    val resultFlow: SharedFlow<List<ObjectDetectionResult>>
}