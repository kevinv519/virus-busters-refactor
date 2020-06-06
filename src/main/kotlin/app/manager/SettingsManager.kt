package app.manager

import app.enums.SettingsOptions
import org.apache.logging.log4j.LogManager
import java.io.File

object SettingsManager {
    private val logger = LogManager.getLogger(SettingsManager::class.java)
    private val languages = arrayOf("en", "es")
    private val playerNames = arrayOf(
            "sprite-player-gama.png",
            "sprite-player-karla.png",
            "sprite-player-kevinl.png",
            "sprite-player-kevinv.png")
    private val settings = mutableMapOf<String, String>()

    private const val FILENAME = ".settings"

    init {
        loadSettings()
    }

    private fun loadSettings() {
        val file = File(FILENAME)
        if (file.exists()) {
            file.forEachLine {
                val entry = it.split("=")
                if (entry.size == 2 && isValidOption(entry[0], entry[1])) {
                    settings[entry[0]] = entry[1]
                } else return@forEachLine
            }
            verifyAndFixSettings()
        } else {
            setUpDefaultSettings()
        }
    }

    private fun setUpDefaultSettings() {
        settings[SettingsOptions.LANGUAGE.label] = languages[0]
        settings[SettingsOptions.PLAYER.label] = playerNames[0]
        settings[SettingsOptions.SFX.label] = "1"
        settings[SettingsOptions.MUSIC.label] = "1"
        updateSettingsFile()
    }

    fun updateSettingsFile() {
        var settingsText = ""
        settings.forEach { (key, value) -> settingsText += "$key=$value\n"}
        File(FILENAME).writeText(settingsText.dropLast(1))
    }

    private fun verifyAndFixSettings() {
        if (settings.size != SettingsOptions.values().size) {
            logger.warn("We detected some problems in the settings file and it will be replaced with the default settings")
            settings.clear()
            setUpDefaultSettings()
        }
    }

    private fun isValidOption(key: String, value: String): Boolean {
        return when (SettingsOptions.values().firstOrNull { it.label == key }) {
            SettingsOptions.LANGUAGE -> languages.contains(value)
            SettingsOptions.PLAYER -> playerNames.contains(value)
            SettingsOptions.SFX, SettingsOptions.MUSIC -> value == "1" || value == "0"
            else -> false
        }
    }

    fun getLanguage() = settings.getOrDefault(SettingsOptions.LANGUAGE.label, languages[0])

    fun getPlayer() = settings.getOrDefault(SettingsOptions.PLAYER.label, playerNames[0])

    fun isMusicEnabled() = settings.getOrDefault(SettingsOptions.MUSIC.label, "0") == "1"

    fun isSfxEnabled() = settings.getOrDefault(SettingsOptions.SFX.label, "0") == "1"
}