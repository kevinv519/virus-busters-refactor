package app.manager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Kevin
 */
public class SettingsManager {
    private final String FILENAME = ".settings";
    
    private HashMap<String,String> settings;
    
    private final String languages[] = {"es", "en"};
    private final String playerNames[] = {"spriteKL.png", "spriteGM.png", "spriteKC.png", "spriteCM.png", "spriteKV.png"};
    
    private static SettingsManager settingsInstance;
    
    public static SettingsManager getInstance(){
        if (settingsInstance == null){
            settingsInstance = new SettingsManager();
        }
        return settingsInstance;
    }
    
    private SettingsManager(){
        settings = new HashMap<>();
        loadSettings();
    }
    
    public String getLanguage(){
       return existsAndContains("language", languages)?
               settings.get("language") : languages[0];
    }
    
    public String getPlayer(){
        return existsAndContains("player", playerNames)? 
                settings.get("player") : playerNames[new java.util.Random().nextInt(playerNames.length-1)];
    }
    
    public void loadSettings(){
        File file = new File(FILENAME);
        try{
            if (file.exists()){
                Scanner sc = new Scanner(file);
                String line;
                while (sc.hasNext()){
                    line = sc.nextLine();
                    settings.put(line.split("=")[0], line.split("=")[1]);
                }
                AudioManager.getInstance().sfx_enable = Integer.valueOf(settings.get("sfx"))==1;
                AudioManager.getInstance().music_enable = Integer.valueOf(settings.get("music"))==1;
            }
            else{
                PrintWriter pw = new PrintWriter(file);
                pw.println("language="+languages[0]);
                pw.println("player="+playerNames[0]);
                pw.println("sfx="+1);
                pw.println("music="+1);
                pw.close();
                loadSettings();
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    private boolean existsAndContains(String key, String list[]){
        if (settings.containsKey(key) && java.util.Arrays.asList(list).contains(settings.get(key)))
            return true;
        return false;
    }
}
