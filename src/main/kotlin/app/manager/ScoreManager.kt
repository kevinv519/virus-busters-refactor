package app.manager

import java.io.File
import kotlin.math.min

object ScoreManager {
    private const val LIST_SIZE = 10
    private const val FILENAME = ".score"

    private var lowerScoreIndex = 0

    val scores: IntArray
    val names: Array<String>

    init {
        scores = IntArray(LIST_SIZE) { 0 }
        names = Array(LIST_SIZE) { "N/A" }
        loadScores()
    }

    private fun loadScores() {
        val file = File(FILENAME)
        if (file.exists()) {
            var i = 0
            file.readLines().take(LIST_SIZE).forEach { line ->
                val entry = line.split("=")
                if (entry.size == 2) {
                    names[i] = entry[0].substring(0, min(entry[0].length, 6))
                    scores[i] = entry[1].toInt()
                    i++
                }
            }
            lowerScoreIndex = if (i == LIST_SIZE) scores.lastIndex else i
        }
    }

    private fun writeScores() {
        var scoresText = ""
        scores.forEachIndexed { i , score ->
            if (score > 0) {
                scoresText += "${names[i]}=$score\n"
            } else return@forEachIndexed
        }
        File(FILENAME).writeText(scoresText.dropLast(1))
    }

    fun isHighScore(score: Int) = score >= scores[lowerScoreIndex]

    fun insertNewScore(name: String, score: Int) {
        var newScoreIndex = lowerScoreIndex
        for (i in lowerScoreIndex downTo 0) {
            if (score >= scores[i]) {
                if (i < scores.lastIndex) {
                    scores[i + 1] = scores[i]
                    names[i + 1] = names[i]
                    newScoreIndex = i
                }
            } else break
        }
        scores[newScoreIndex] = score
        names[newScoreIndex] = name.substring(0, min(name.length, 6))
        writeScores()

        if (lowerScoreIndex < scores.lastIndex) {
            lowerScoreIndex++
        }
    }
}