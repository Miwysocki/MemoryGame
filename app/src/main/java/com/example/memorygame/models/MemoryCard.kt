package com.example.memorygame.models

data class MemoryCard(
        val identifier: Int,
        var FaceUp: Boolean = false,
        var isMatched: Boolean = false
)