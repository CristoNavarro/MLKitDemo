package com.example.mlkitdemo.infra.imageAnalyzer

import android.content.Context
import android.graphics.Matrix
import android.util.Size
import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.core.ImageProxy
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.core.content.ContextCompat
import com.example.mlkitdemo.data.mappers.toTextResult
import com.example.mlkitdemo.domain.imageAnalyzer.TextRecognitionImageAnalyzer
import com.example.mlkitdemo.domain.models.TextResult
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TextRecognitionImageAnalyzerImpl(private val context: Context): TextRecognitionImageAnalyzer {
    private val _resultFlow: MutableSharedFlow<TextResult> = MutableSharedFlow()
    override val resultFlow: SharedFlow<TextResult> = _resultFlow.asSharedFlow()

    private val analyzer = buildAnalyzer()

    override fun analyze(image: ImageProxy) {
        analyzer.analyze(image)
    }

    override fun getTargetCoordinateSystem(): Int {
        return analyzer.targetCoordinateSystem
    }

    override fun getDefaultTargetResolution(): Size {
        return analyzer.defaultTargetResolution
    }

    override fun updateTransform(matrix: Matrix?) {
        analyzer.updateTransform(matrix)
    }

    private fun buildAnalyzer(): MlKitAnalyzer {
        val scope = CoroutineScope(Dispatchers.IO)
        val scanner = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        return MlKitAnalyzer(
            listOf(scanner),
            COORDINATE_SYSTEM_VIEW_REFERENCED,
            ContextCompat.getMainExecutor(context),
        ) { result ->
            result.getValue(scanner)?.let {
                scope.launch {
                    _resultFlow.emit(it.toTextResult())
                }
            }
        }
    }
}