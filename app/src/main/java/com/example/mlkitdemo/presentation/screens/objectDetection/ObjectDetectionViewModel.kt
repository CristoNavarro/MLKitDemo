package com.example.mlkitdemo.presentation.screens.objectDetection

import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mlkitdemo.domain.imageAnalyzer.ObjectDetectionImageAnalyzer
import com.example.mlkitdemo.domain.models.ObjectDetectionResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ObjectDetectionViewState(
    val results: List<ObjectDetectionResult> = emptyList(),
    val analyzer: Analyzer? = null
)

class ObjectDetectionViewModel(analyzer: ObjectDetectionImageAnalyzer): ViewModel() {
    val state: StateFlow<ObjectDetectionViewState> = analyzer.resultFlow.map {
        ObjectDetectionViewState(
            results = it
        )
    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ObjectDetectionViewState(analyzer = analyzer)
    )
}
