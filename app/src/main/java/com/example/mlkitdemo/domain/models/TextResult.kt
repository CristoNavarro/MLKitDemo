package com.example.mlkitdemo.domain.models

data class TextResult(
    val text: String,
    val blocks: List<TextResultBlock>
)

