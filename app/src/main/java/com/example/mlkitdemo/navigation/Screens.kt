package com.example.mlkitdemo.navigation

sealed class Screens(val route: String) {
    data object Home: Screens(route = "home")
    data object TextFromCamera: Screens(route = "textFromCamera")
    data object TextFromImage: Screens(route = "textFromImage")
    data object ImageLabeling: Screens(route = "imageLabeling")
    data object LanguageRecognition: Screens(route = "languageRecognition")
    data object TextTranslation: Screens(route = "textTranslation")
    data object ObjectDetection: Screens(route = "objectDetection")
}
