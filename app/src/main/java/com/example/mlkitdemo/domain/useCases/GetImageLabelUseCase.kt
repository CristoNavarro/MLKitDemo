package com.example.mlkitdemo.domain.useCases

import androidx.camera.core.ImageProxy
import com.example.mlkitdemo.domain.models.ImageLabelResult
import com.example.mlkitdemo.domain.repository.ImageAnalysisRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GetImageLabelUseCase(private val imageAnalyzer: ImageAnalysisRepository) {
    suspend operator fun invoke(image: ImageProxy): List<ImageLabelResult> {
        val scope = CoroutineScope(Dispatchers.IO)
        return scope.async {
            imageAnalyzer.getLabelFrom(image)
        }.await()
    }
}