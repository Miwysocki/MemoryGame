package com.example.memorygame.models

import com.example.memorygame.utils.DEFAULT_ICONS

class Game(private val boardSize: BoardSize) {


    fun flipCard(position: Int): Boolean {
        numberOfCardFlip++
        val card = cards[position]
        var foundMatch = false
        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            //1 karta
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.FaceUp = !card.FaceUp
        return foundMatch
    }

    private fun checkForMatch(pos1: Int, pos2: Int): Boolean {
        if (cards[pos1].identifier != cards[pos2].identifier) {
            return false
        }
        cards[pos1].isMatched = true
        cards[pos2].isMatched = true
        numberOfPairsFound++
        return true
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched)
                card.FaceUp = false
        }
    }

    fun haveWonGame(): Boolean {
        if (numberOfPairsFound == boardSize.getNumberOfPairs()) return true
        else return false
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].FaceUp

    }

    fun getNumberOFMoves(): Int {
        return numberOfCardFlip / 2
    }


    val cards: List<MemoryCard>
    var numberOfPairsFound = 0
    private var numberOfCardFlip = 0;
    private var indexOfSingleSelectedCard: Int? = null

    init {

        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumberOfPairs())
        val randomizeImages = (chosenImages + chosenImages).shuffled()
        cards = randomizeImages.map { MemoryCard(it, false, false) }
    }
}