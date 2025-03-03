package com.example.mlkitdemo.di

import com.example.mlkitdemo.infra.imageAnalyzer.ObjectDetectionImageAnalyzerImpl
import com.example.mlkitdemo.data.repository.ImageAnalysisRepositoryImpl
import com.example.mlkitdemo.data.repository.NaturalLanguageRepositoryImpl
import com.example.mlkitdemo.domain.imageAnalyzer.ObjectDetectionImageAnalyzer
import com.example.mlkitdemo.domain.imageAnalyzer.TextRecognitionImageAnalyzer
import com.example.mlkitdemo.domain.repository.ImageAnalysisRepository
import com.example.mlkitdemo.domain.repository.NaturalLanguageRepository
import com.example.mlkitdemo.domain.useCases.GetTextFromImageUseCase
import com.example.mlkitdemo.domain.useCases.GetImageLabelUseCase
import com.example.mlkitdemo.domain.useCases.GetLanguageUseCase
import com.example.mlkitdemo.domain.useCases.GetTranslationUseCase
import com.example.mlkitdemo.infra.imageAnalyzer.TextRecognitionImageAnalyzerImpl
import com.example.mlkitdemo.presentation.screens.textFromImage.TextFromImageViewModel
import com.example.mlkitdemo.presentation.screens.textFromCamera.TextFromCameraViewModel
import com.example.mlkitdemo.presentation.screens.imageLabeling.ImageLabelingViewModel
import com.example.mlkitdemo.presentation.screens.languageRecognition.LanguageRecognitionViewModel
import com.example.mlkitdemo.presentation.screens.objectDetection.ObjectDetectionViewModel
import com.example.mlkitdemo.presentation.screens.textTranslation.TextTranslationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

private val appModule = module {
    singleOf(::ImageAnalysisRepositoryImpl).bind<ImageAnalysisRepository>()
    singleOf(::NaturalLanguageRepositoryImpl).bind<NaturalLanguageRepository>()
//    singleOf(::ObjectDetectionImageAnalyzerImpl).bind<ObjectDetectionImageAnalyzer>()

    single {
        ObjectDetectionImageAnalyzerImpl(androidContext())
    }.bind<ObjectDetectionImageAnalyzer>()

    single {
        TextRecognitionImageAnalyzerImpl(androidContext())
    }.bind<TextRecognitionImageAnalyzer>()

    singleOf(::GetTextFromImageUseCase)
    singleOf(::GetImageLabelUseCase)
    singleOf(::GetLanguageUseCase)
    singleOf(::GetTranslationUseCase)

    viewModelOf(::TextFromImageViewModel)
    viewModelOf(::TextFromCameraViewModel)
    viewModelOf(::ImageLabelingViewModel)
    viewModelOf(::LanguageRecognitionViewModel)
    viewModelOf(::TextTranslationViewModel)
    viewModelOf(::ObjectDetectionViewModel)
}

fun startDI(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule)
    }
}