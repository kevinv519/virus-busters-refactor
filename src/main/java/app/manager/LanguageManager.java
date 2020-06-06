package app.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Kevin
 */
public class LanguageManager {
    private static final Logger logger = LogManager.getLogger(LanguageManager.class);
    
    private ResourceBundle bundle;
    
    private LanguageManager(){
        var locale = new Locale(SettingsManager.INSTANCE.getLanguage());
        bundle = ResourceBundle.getBundle("locale/strings", locale);
    }

    private static class LanguageManagerHelper {
        private static LanguageManager INSTANCE = new LanguageManager();
    }
    
    public static LanguageManager getInstance(){
        return LanguageManagerHelper.INSTANCE;
    }

    /**
     * Gets the string value for the given key and the user preferred language.
     * @param key of the string value.
     * @return The string value, or a default message if the key cannot be found.
     */
    public String get(String key){
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            logger.warn("Property with key '" + key + "' was not found.", e);
            return bundle.getString("key_not_found");
        }
    }
    
    public void reloadLanguage(){
        var locale = new Locale(SettingsManager.INSTANCE.getLanguage());
        bundle = ResourceBundle.getBundle("locale/strings", locale);
    }
}
