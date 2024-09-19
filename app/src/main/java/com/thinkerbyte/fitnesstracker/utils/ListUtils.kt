package com.thinkerbyte.fitnesstracker.utils

import java.util.Collections

fun <T> List<T>.swap(idx1: Int, idx2: Int): List<T> {
    val temp = this.toMutableList()
    Collections.swap(temp, idx1, idx2)
    return temp
}