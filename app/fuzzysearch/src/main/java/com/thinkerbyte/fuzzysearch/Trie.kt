package com.thinkerbyte.fuzzysearch

import java.security.InvalidParameterException
import java.util.Locale

class Trie {
    val root: Node = Node(null, '#')

    fun insertWord(word: String) {
        val preprocessedWord = word.lowercase(Locale.ROOT)
        checkStringContainsOnlyLetters(preprocessedWord)

        var currentNode = root
        for (character in preprocessedWord) {
            currentNode = currentNode.getChild(character)
        }
        currentNode.markFinal()
    }

    private fun checkStringContainsOnlyLetters(word: String) {
        if(!word.all { it.isLetter() }) {
            throw InvalidParameterException("You can't insert in the Trie a word that contains other characters except letters.")
        }
    }

    class Node(val parent: Node?, val value: Char) {
        private val children: MutableMap<Char, Node> = mutableMapOf()
        private var isEndOfWord: Boolean = false

        fun getChild(character: Char): Node {
            if(!children.containsKey(character)) {
                children[character] = Node(this, character)
            }
            return children[character]!!
        }

        fun getChildren(): List<Node> {
            return children.values.toList()
        }



        fun markFinal() {
            isEndOfWord = true
        }

        fun isEndOfWord(): Boolean = isEndOfWord
    }
}

fun main() {
    val trie = Trie()
    trie.insertWord("cat")
    trie.insertWord("catnip")
    trie.insertWord("catalog")
}



