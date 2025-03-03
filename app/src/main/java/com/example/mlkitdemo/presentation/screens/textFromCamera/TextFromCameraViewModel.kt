package com.example.mlkitdemo.presentation.screens.textFromCamera

import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mlkitdemo.domain.imageAnalyzer.TextRecognitionImageAnalyzer
import com.example.mlkitdemo.domain.models.TextResultBlock
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class TextFromCameraViewState(
    val textBlocks: List<TextResultBlock> = emptyList(),
    val analyzer: Analyzer? = null
)

class TextFromCameraViewModel(analyzer: TextRecognitionImageAnalyzer): ViewModel() {
    val state: StateFlow<TextFromCameraViewState> = analyzer.resultFlow.map {
        TextFromCameraViewState(
            textBlocks = it.blocks
        )
    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TextFromCameraViewState(analyzer = analyzer)
    )
}
