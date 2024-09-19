package com.thinkerbyte.fitnesstracker.utils

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReadJsonFromAssetsKtTest {

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun read() {
        val jsonString = """[{"id": 1, "description": "Desc 1"}, {"id": 2, "description": "Desc 2"}]"""

        val output = readJsonFromAssets(appContext, "test.json")
        assertEquals(jsonString, output)
    }
}