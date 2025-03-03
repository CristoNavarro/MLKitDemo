package com.example.mlkitdemo.presentation.screens.imageLabeling

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mlkitdemo.domain.models.ImageLabelResult
import com.example.mlkitdemo.domain.useCases.GetImageLabelUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ImageLabelingViewState(
    var imageLabels: List<ImageLabelResult> = emptyList(),
    var isLoading: Boolean = false,
    val showBottomSheet: Boolean = false
)

class ImageLabelingViewModel(private val useCase: GetImageLabelUseCase): ViewModel() {
    private val _state = MutableStateFlow(ImageLabelingViewState())
    val state: StateFlow<ImageLabelingViewState> = _state.asStateFlow()

    fun analyzeImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = useCase.invoke(imageProxy)
            _state.update { it.copy(imageLabels = result, isLoading = false) }
        }
    }

    fun showBottomSheet() {
        _state.update { it.copy(showBottomSheet = true) }
    }

    fun hideBottomSheet() {
        _state.update { it.copy(showBottomSheet = false) }
    }
}