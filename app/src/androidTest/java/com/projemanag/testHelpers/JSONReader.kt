package com.projemanag.testHelpers

import androidx.test.platform.app.InstrumentationRegistry

fun readJSONFromAsset(file: String): String? {
    var json: String? = null
    try {
        val inputStream = InstrumentationRegistry.getInstrumentation().context.assets.open(file)
        json = inputStream.bufferedReader().use { it.readText() }
    } catch (ex: Exception) {
        ex.printStackTrace()
        return null
    }
    return json
}

fun getFromFile(fileName: String): String? {
    return readJSONFromAsset(fileName)
}
