package com.example.mlkitdemo.presentation.screens.languageRecognition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mlkitdemo.domain.models.LanguageRecognitionResult
import com.example.mlkitdemo.domain.useCases.GetLanguageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LanguageRecognitionViewState(
    val detectedLanguages: List<LanguageRecognitionResult> = emptyList(),
    val isLoading: Boolean = false,
    val text: String = ""
)

class LanguageRecognitionViewModel(private val useCase: GetLanguageUseCase): ViewModel() {
    private val _state = MutableStateFlow(LanguageRecognitionViewState())
    val state: StateFlow<LanguageRecognitionViewState> = _state.asStateFlow()

    fun updateText(text: String) {
        _state.update { it.copy(text = text) }
    }

    fun getLanguage() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = useCase(_state.value.text)
            _state.update { it.copy(detectedLanguages = result, isLoading = false) }
        }
    }
}