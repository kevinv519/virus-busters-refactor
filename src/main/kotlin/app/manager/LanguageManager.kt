package app.manager

import org.apache.logging.log4j.LogManager
import java.util.*

object LanguageManager {
    private val logger = LogManager.getLogger(LanguageManager::class.java)

    private const val BUNDLE_NAME = "locale/strings"
    private var bundle: ResourceBundle

    init {
        val locale = Locale(SettingsManager.getLanguage())
        bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale)
    }

    fun get(key: String): String {
        try {
            return bundle.getString(key)
        } catch (e: Exception) {
            logger.warn("Property with key $key was not found.", e)
            return bundle.getString("key_not_found")
        }
    }

    fun reloadLanguage() {
        val locale = Locale(SettingsManager.getLanguage())
        bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale)
    }
}