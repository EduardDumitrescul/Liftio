package com.thinkerbyte.fuzzysearch

class FuzzySearch(
    private val searchItems: List<String>,
) {

    private val indexDictionary: MutableMap<String, MutableList<Int>> = mutableMapOf()
    private var damerauLevenshteinTrieSearch: DamerauLevenshteinTrieSearch
    private val trie: Trie = Trie()

    init {
        createIndexDictionary()
        populateTrie()
        damerauLevenshteinTrieSearch = DamerauLevenshteinTrieSearch(trie)
    }

    private fun createIndexDictionary() {
        searchItems.forEachIndexed { index, entry ->
            entry
                .lowercase()
                .splitWords()
                .forEach { word ->
                    addWordToDictionary(word, index)
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
        val words = sentence.lowercase().splitWords()
        val scoreMap: MutableMap<Int, Float> = mutableMapOf()
        searchItems.forEachIndexed {index, _ -> scoreMap.putIfAbsent(index, 0f) }
        words.forEach { word ->
            val similarityMap = searchWordMatch(word)
            similarityMap.entries.forEach { (index, score) ->
                scoreMap[index] = scoreMap[index]!! + score
            }
        }

        return scoreMap
            .toList()
            .sortedBy { -it.second }
            .map { it.first }
    }

    // Given a word, find the ids of all the search entries that contain the most similar words, based on Damerau-Levenshtein Distance
    private fun searchWordMatch(word: String): Map<Int, Float> {
        val similarWords = damerauLevenshteinTrieSearch.getSimilarWords(word)
        val similarityMap: MutableMap<Int, Float> = mutableMapOf()
        similarWords.forEach { (word, score) ->
            try {
                val indexes = indexDictionary[word]!!
                indexes.forEach { index ->
                    similarityMap.putIfAbsent(index, 0f)
                    similarityMap[index] = similarityMap[index]!! + score
                }
            }
            catch (exception: Exception) {
                throw Exception(indexDictionary.toString())
            }

        }

        return similarityMap
    }

//    private fun List<Int>.removeDuplicates() = this.toSet().toList()

    private fun String.filterLetters(): String {
        return this.map { if(!it.isLetter()) ' ' else it }.joinToString("")
    }

    private fun String.splitWords() =
        this.filterLetters()
            .split(" ")
            .filter { it.isNotBlank() }

}

fun main() {
    val f = FuzzySearch(listOf("bench press"))
    f.search("biceps")
}