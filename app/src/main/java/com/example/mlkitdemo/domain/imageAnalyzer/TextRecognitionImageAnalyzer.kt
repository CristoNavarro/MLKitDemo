package com.example.mlkitdemo.domain.imageAnalyzer

import androidx.camera.core.ImageAnalysis.Analyzer
import com.example.mlkitdemo.domain.models.TextResult
import kotlinx.coroutines.flow.SharedFlow

interface TextRecognitionImageAnalyzer: Analyzer {
    val resultFlow: SharedFlow<TextResult>
}