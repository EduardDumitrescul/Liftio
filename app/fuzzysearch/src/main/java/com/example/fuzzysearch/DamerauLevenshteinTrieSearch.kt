package com.example.fuzzysearch

import java.util.Stack
import kotlin.math.exp
import kotlin.math.min

class DamerauLevenshteinTrieSearch(
    private val trie: Trie,
) {
    private var searchWord: String = ""
    private var letterStack: MutableList<Char> = mutableListOf()
    private var bestDistanceFound: Int = Int.MAX_VALUE
    private val similarWords: MutableList<Pair<String, Float>> = mutableListOf()
    private val distanceMatrix: MutableList<MutableList<Int>> = mutableListOf()

    fun getSimilarWords(word: String): List<Pair<String, Float>> {
        bestDistanceFound = Int.MAX_VALUE
        distanceMatrix.clear()
        similarWords.clear()
        this.searchWord = "_$word"

        searchRecursive(
            node = trie.root,
            depth = 0
        )

        return similarWords
    }

    private fun searchRecursive(node: Trie.Node, depth: Int) {
        distanceMatrix.add(MutableList(searchWord.length) {Int.MAX_VALUE})

        for(i in searchWord.indices) {
            // Case 0: empty words
            if(i == 0 && depth == 0) {
                distanceMatrix[depth][i] = 0
            }
            // Case 1: deletion from search word
            try { distanceMatrix[depth][i] = min(distanceMatrix[depth][i], distanceMatrix[depth][i-1] + 2) }
            catch (ignored: Exception) {}

            // Case 2: deletion from trie word
            try { distanceMatrix[depth][i] = min(distanceMatrix[depth][i], distanceMatrix[depth-1][i] + 1) }
            catch (ignored: Exception) {}

            // Case 3: identical/different last characters
            try { distanceMatrix[depth][i] = min(distanceMatrix[depth][i], distanceMatrix[depth-1][i-1] + if(searchWord[i] == node.value) 0 else 3) }
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
            if(distance < 5)
            similarWords.add(Pair(letterStack.joinToString(""), exp(-distance.toDouble()).toFloat()))
        }

        for(nextNode in node.getChildren()) {
            letterStack.add(nextNode.value)
            searchRecursive(nextNode, depth+1)
            letterStack.removeLast()
        }

        distanceMatrix.removeLast()

    }
}