package com.example.mlkitdemo.domain.useCases

import androidx.camera.core.ImageProxy
import com.example.mlkitdemo.domain.repository.ImageAnalysisRepository
import com.example.mlkitdemo.domain.models.TextResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GetTextFromImageUseCase(private val imageAnalyzer: ImageAnalysisRepository) {
    suspend operator fun invoke(image: ImageProxy): TextResult {
        val scope = CoroutineScope(Dispatchers.IO)
        return scope.async {
            imageAnalyzer.getTextFrom(image)
        }.await()
    }
}