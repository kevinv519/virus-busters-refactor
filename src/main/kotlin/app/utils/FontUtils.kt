package app.utils

import javafx.scene.text.Font

private const val PATH = "fonts/"

val NINTENDER = "nintender.ttf"
val PUSAB = "pusab.ttf"

fun loadFont(fontName: String, fontSize: Double) : Font {
    val inputStream = ClassLoader.getSystemResourceAsStream(PATH + fontName)
    return Font.loadFont(inputStream, fontSize) ?: Font.font(fontSize)
}