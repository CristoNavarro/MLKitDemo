package com.example.mlkitdemo.infra.imageAnalyzer

import android.content.Context
import android.graphics.Matrix
import android.util.Size
import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.core.ImageProxy
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.core.content.ContextCompat
import com.example.mlkitdemo.data.mappers.toObjectDetectionResult
import com.example.mlkitdemo.domain.imageAnalyzer.ObjectDetectionImageAnalyzer
import com.example.mlkitdemo.domain.models.ObjectDetectionResult
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ObjectDetectionImageAnalyzerImpl(private val context: Context): ObjectDetectionImageAnalyzer {
    private val _resultFlow: MutableSharedFlow<List<ObjectDetectionResult>> = MutableSharedFlow()
    override val resultFlow: SharedFlow<List<ObjectDetectionResult>> = _resultFlow.asSharedFlow()

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
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
            .enableClassification()
            .build()
        val scanner = ObjectDetection.getClient(options)
        return MlKitAnalyzer(
            listOf(scanner),
            COORDINATE_SYSTEM_VIEW_REFERENCED,
            ContextCompat.getMainExecutor(context),
        ) { result ->
            result.getValue(scanner)?.let {
                scope.launch {
                    _resultFlow.emit(it.toObjectDetectionResult())
                }
            }
        }
    }
}