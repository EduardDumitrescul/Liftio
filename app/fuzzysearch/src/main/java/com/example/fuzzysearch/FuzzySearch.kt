package com.example.fuzzysearch

class FuzzySearch(
    private val searchItems: List<SearchEntry>,
    typoThreshold: Int = 3,
) {

    private val indexDictionary: MutableMap<String, MutableList<Int>> = mutableMapOf()
    private var damerauLevenshteinTrieSearch: DamerauLevenshteinTrieSearch
    private val trie: Trie = Trie()

    init {
        createIndexDictionary()
        populateTrie()
        damerauLevenshteinTrieSearch = DamerauLevenshteinTrieSearch(trie, typoThreshold)
    }

    private fun createIndexDictionary() {
        searchItems.forEach { entry ->
            entry.value
                .splitWords()
                .forEach { word ->
                    addWordToDictionary(word, entry.id)
            }
        }
    }

    private fun populateTrie() {
        indexDictionary.keys.forEach { word ->
            trie.insertWord(word)
        }
    }

    private fun addWordToDictionary(word: String, entryId: Int) {
        if(!indexDictionary.containsKey(word)) {
            indexDictionary[word] = mutableListOf()
        }
        indexDictionary[word]!!.add(entryId)
    }

    fun search(sentence: String): List<Int> {
        val words = sentence.splitWords()
        val scoreMap: MutableMap<Int, Int> = mutableMapOf()
        words.forEach { word ->
            val ids = searchWordMatch(word)
            ids.forEach { id ->
                scoreMap.putIfAbsent(id, 0)
                scoreMap[id] = scoreMap[id]!! + 1
            }
        }

        return scoreMap
            .toList()
            .sortedBy { -it.second }
            .map { it.first }
    }

    // Given a word, find the ids of all the search entries that contain the most similar words, based on Damerau-Levenshtein Distance
    private fun searchWordMatch(word: String): List<Int> {
        val similarWords = damerauLevenshteinTrieSearch.getSimilarWords(word)
        return similarWords
            .flatMap { indexDictionary[it]!! }
            .removeDuplicates()
    }

    private fun List<Int>.removeDuplicates() = this.toSet().toList()

    private fun String.filterLetters(): String {
        return this.map { if(!it.isLetter()) ' ' else it }.joinToString("")
    }

    private fun String.splitWords() =
        this.filterLetters()
            .split(" ")
            .filter { it.isNotBlank() }

    class SearchEntry(
        val id: Int,
        val value: String
    )
}