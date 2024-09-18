package com.example.fuzzysearch

import java.util.Stack
import kotlin.math.min

class DamerauLevenshteinTrieSearch(
    private val trie: Trie,
    private val maxDistanceDiff: Int,
) {
    private var searchWord: String = ""
    private var letterStack: MutableList<Char> = mutableListOf()
    private var bestDistanceFound: Int = Int.MAX_VALUE
    private val similarWords: MutableList<String> = mutableListOf()
    private val distanceMatrix: MutableList<MutableList<Int>> = mutableListOf()

    fun getSimilarWords(word: String): List<String> {
        bestDistanceFound = Int.MAX_VALUE
        distanceMatrix.clear()
        this.searchWord = "_$word"

        searchRecursive(
            node = trie.root,
            depth = 0
        )

        return similarWords
    }

    private fun searchRecursive(node: Trie.Node, depth: Int) {
        distanceMatrix.add(MutableList(searchWord.length) {Int.MAX_VALUE})

        for(i in 0 until searchWord.length) {
            // Case 0: empty words
            if(i == 0 && depth == 0) {
                distanceMatrix[depth][i] = 0
            }
            // Case 1: deletion from trie word
            try { distanceMatrix[depth][i] = min(distanceMatrix[depth][i], distanceMatrix[depth][i-1] + 1) }
            catch (ignored: Exception) {}

            // Case 2: deletion from search word
            try { distanceMatrix[depth][i] = min(distanceMatrix[depth][i], distanceMatrix[depth-1][i] + 1) }
            catch (ignored: Exception) {}

            // Case 3: identical/different last characters
            try { distanceMatrix[depth][i] = min(distanceMatrix[depth][i], distanceMatrix[depth-1][i-1] + if(searchWord[i] == node.value) 0 else 1) }
            catch (ignored: Exception) {}

            // Case 4: transposition of the last two characters from each word
            try {
                if(searchWord[i] == letterStack[depth-2] && searchWord[i-1] == letterStack[depth-1]) {
                    distanceMatrix[depth][i] = min(distanceMatrix[depth][i], distanceMatrix[depth-2][i-2] + 1)
                }
            }
            catch (ignored: Exception) {}
        }

        if(node.isEndOfWord()) {
            val distance = distanceMatrix.last().last()
            if(maxDistanceDiff >= distance) {
                similarWords.add(letterStack.joinToString(""))
            }
        }

        for(nextNode in node.getChildren()) {
            letterStack.add(nextNode.value)
            searchRecursive(nextNode, depth+1)
            letterStack.removeLast()
        }

        distanceMatrix.removeLast()

    }
}