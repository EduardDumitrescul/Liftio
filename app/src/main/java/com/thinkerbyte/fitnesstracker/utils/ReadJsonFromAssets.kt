package com.thinkerbyte.fitnesstracker.utils

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

fun readJsonFromAssets(context: Context, path: String): String {
    val identifier = "[ReadJSON]"
    try {
        val file = context.assets.open(path)
        val bufferedReader = BufferedReader(InputStreamReader(file))
        val stringBuilder = StringBuilder()
        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
        }
        return stringBuilder.toString()
    } catch (e: Exception) {
        Log.e(
            identifier,
            "Error reading JSON: $e.",
        )
        e.printStackTrace()
        return ""
    }
}