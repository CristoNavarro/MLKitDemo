package com.example.mlkitdemo.domain.repository

import androidx.camera.core.ImageProxy
import com.example.mlkitdemo.domain.models.ImageLabelResult
import com.example.mlkitdemo.domain.models.TextResult

interface ImageAnalysisRepository {
    suspend fun getTextFrom(imageProxy: ImageProxy): TextResult
    suspend fun getLabelFrom(imageProxy: ImageProxy): List<ImageLabelResult>
}