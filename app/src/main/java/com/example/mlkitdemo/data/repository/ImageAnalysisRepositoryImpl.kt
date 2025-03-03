package com.example.mlkitdemo.data.repository

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.example.mlkitdemo.data.mappers.toImageLabelResult
import com.example.mlkitdemo.data.mappers.toTextResult
import com.example.mlkitdemo.domain.models.ImageLabelResult
import com.example.mlkitdemo.domain.models.TextResult
import com.example.mlkitdemo.domain.repository.ImageAnalysisRepository
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class ImageAnalysisRepositoryImpl : ImageAnalysisRepository {
    override suspend fun getTextFrom(imageProxy: ImageProxy): TextResult {
        getInputImageFrom(imageProxy)?.let { inputImage ->
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val task = recognizer.process(inputImage)
            Tasks.await(task)
            return task.result.toTextResult()
        }
        return TextResult(text = "", blocks = emptyList())
    }

    override suspend fun getLabelFrom(imageProxy: ImageProxy): List<ImageLabelResult> {
        getInputImageFrom(imageProxy)?.let { inputImage ->
            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
            val task = labeler.process(inputImage)
            Tasks.await(task)
            return task.result.toImageLabelResult()
        }
        return emptyList()
    }

    @OptIn(ExperimentalGetImage::class)
    private fun getInputImageFrom(imageProxy: ImageProxy): InputImage? {
        imageProxy.image?.let {
            return InputImage.fromMediaImage(
                it,
                imageProxy.imageInfo.rotationDegrees
            )
        }
        return null
    }
}

