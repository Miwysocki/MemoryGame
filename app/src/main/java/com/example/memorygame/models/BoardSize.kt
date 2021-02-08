package com.example.memorygame.models

enum class BoardSize(val numberOfCards: Int) {
    NORMAL(18),
    BIG(24);

    fun getWidth(): Int {
        return when (this) {
            NORMAL -> 3
            BIG -> 4
        }
    }

    fun getHeight(): Int {
        return numberOfCards / getWidth()
    }

    fun getNumberOfPairs(): Int {
        return numberOfCards / 2
    }
}