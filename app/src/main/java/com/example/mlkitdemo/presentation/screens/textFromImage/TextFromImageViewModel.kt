package com.example.mlkitdemo.presentation.screens.textFromImage

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mlkitdemo.domain.models.TextResult
import com.example.mlkitdemo.domain.useCases.GetTextFromImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TextFromImageViewState (
    val resultText: String = "",
    val isLoading: Boolean = false,
    val showBottomSheet: Boolean = false
)

class TextFromImageViewModel(private val useCase: GetTextFromImageUseCase): ViewModel() {
    private val _state = MutableStateFlow(TextFromImageViewState())
    val state: StateFlow<TextFromImageViewState> = _state.asStateFlow()

    fun analyzeImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = useCase.invoke(imageProxy)
            _state.update { it.copy(resultText = format(result), isLoading = false) }
        }
    }

    fun showBottomSheet() {
        _state.update { it.copy(showBottomSheet = true) }
    }

    fun hideBottomSheet() {
        _state.update { it.copy(showBottomSheet = false) }
    }

    private fun format(result: TextResult): String {
        return result.blocks.joinToString("\n") { block ->
            block.text.replace("\n", "")
        }
    }
}