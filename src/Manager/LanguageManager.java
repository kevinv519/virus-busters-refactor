package Manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Kevin
 */
public class LanguageManager {
    private static LanguageManager instance;
    
    private ResourceBundle bundle;
    
    private LanguageManager(){
        bundle = ResourceBundle.getBundle("Strings/Etiquetas_"+SettingsManager.getInstance().getLanguage());
    }
    
    public static LanguageManager getInstance(){
        if (instance == null){
            instance = new LanguageManager();
        }
        return instance;
    }
    
    public String get(String key){
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }
    }
    
    public void reloadLanguage(){
        bundle = ResourceBundle.getBundle("Strings/Etiquetas_"+SettingsManager.getInstance().getLanguage());
    }
}
