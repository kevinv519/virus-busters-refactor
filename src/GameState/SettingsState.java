package GameState;

import Main.GamePanel;
import Manager.AudioManager;
import Manager.GameStateManager;
import Manager.KeyManager;
import Manager.LanguageManager;
import Manager.SettingsManager;
import Manager.TextManager;
import Manager.TextureManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SettingsState extends GameState {
 
    private final int SETTINGS_STATE = 4;
    
    private int currentOption;
    private int languageOption;
    private int playerOption;
    private int musicOption;
    private int sfxOption;
    
    public SettingsState(GameStateManager gsm){
        super(gsm);
    }
    
    private final String playerNames[] = {"spriteKL.png", "spriteGM.png", "spriteKC.png", "spriteCM.png", "spriteKV.png"};
    private final BufferedImage players[] = new BufferedImage[5];
    private final String langs[] = {"ES", "EN"};
    private final String sound[] = { LanguageManager.getInstance().get("apagado"),
        LanguageManager.getInstance().get("encendido")};
    private final String options[] = {
        LanguageManager.getInstance().get("lenguaje"),
        LanguageManager.getInstance().get("music"),
        LanguageManager.getInstance().get("sfx"),
        LanguageManager.getInstance().get("jugador"),
        LanguageManager.getInstance().get("guardar"),
        LanguageManager.getInstance().get("regresar")
    };
    
    private void loadPlayers(){
        players[0] = TextureManager.spritePlayer[0][0];
        int j =1;
        for (int i = 0; i < playerNames.length; i++) {
            if (playerNames[i] != SettingsManager.getInstance().getPlayer()){
                players[i] = TextureManager.loadSpriteSheet(playerNames[i], 64, 64)[0][0];
                j++;
            }
            
        }
    }
 
    
    @Override
    public void init(){
        currentOption = 0;
        if("ES".equals(SettingsManager.getInstance().getLanguage()))
            languageOption = 0;
        else if("EN".equals(SettingsManager.getInstance().getLanguage()))
            languageOption = 1;
        if(AudioManager.getInstance().music_enable)
            musicOption = 1;
        else musicOption = 0;
        if(AudioManager.getInstance().sfx_enable)
            sfxOption = 1;
        else sfxOption = 0;
        loadPlayers();
        for (int i = 0; i < playerNames.length; i++) {
            if (playerNames[i].equals(SettingsManager.getInstance().getPlayer()))
                playerOption = i;
        }
    }
    
    @Override
    public void update(){
        handleEvents();
    }
    @Override
    public void draw(Graphics2D g){
        g.drawImage(TextureManager.menu, 0, 0,GamePanel.PANEL_WIDTH,GamePanel.PANEL_HEIGHT, null);
        g.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 40));
        for (int i = 0; i < options.length; i++) {
            if (currentOption == i){
                
                g.setColor(Color.RED);
                if(i == 4)
                    g.drawImage(TextureManager.arrow, 120, 465, 35, 35, null);
                else if(i == 5)
                    g.drawImage(TextureManager.arrow, 340, 465
                            , 40, 40, null);
                else
                    g.drawImage(TextureManager.arrow, 120, 195 + currentOption*50, 35, 35, null);
            }
            else{
                g.setColor(Color.BLACK);
            }
            
            if(i == 4 )
                g.drawString(options[i], 160, 500);
            
            else if(i == 5)
                g.drawString(options[i], 380, 500);
            else
                g.drawString(options[i], 160, 230+50*i);
            
            if(i==0)
                g.drawString(langs[languageOption], 435, 230);
            if(i==1)
                g.drawString(sound[musicOption], 435, 280);
            if(i == 2)
                g.drawString(sound[sfxOption], 435, 330);
            
            g.drawImage(players[playerOption], 423, 350, 64, 64, null);
        }
    }
    
    @Override
    public void handleEvents(){
        if (KeyManager.isPressed(KeyManager.ESCAPE)){
            gsm.popState();
        }
        
        if(currentOption == 0){
            if(languageOption == 0){
                if(KeyManager.isPressed(KeyManager.LEFT)||KeyManager.isPressed(KeyManager.RIGHT))
                    languageOption = 1;
            } else if(languageOption == 1){
                if(KeyManager.isPressed(KeyManager.LEFT)||KeyManager.isPressed(KeyManager.RIGHT))
                    languageOption = 0;
            }
        }
        
        if(currentOption == 1){
            if(musicOption == 0){
                if(KeyManager.isPressed(KeyManager.LEFT)||KeyManager.isPressed(KeyManager.RIGHT))
                    musicOption = 1;
            } else if(musicOption == 1){
                if(KeyManager.isPressed(KeyManager.LEFT)||KeyManager.isPressed(KeyManager.RIGHT))
                    musicOption = 0;
            }
        }
        
        if(currentOption == 2){
            if(sfxOption == 0){
                if(KeyManager.isPressed(KeyManager.LEFT)||KeyManager.isPressed(KeyManager.RIGHT))
                    sfxOption = 1;
            } else if(sfxOption == 1){
                if(KeyManager.isPressed(KeyManager.LEFT)||KeyManager.isPressed(KeyManager.RIGHT))
                    sfxOption = 0;
            }
        }
        
        if(currentOption == 3){
            switch(playerOption){
                case 0:
                    if(KeyManager.isPressed(KeyManager.LEFT))
                        playerOption = 4;
                    else if(KeyManager.isPressed(KeyManager.RIGHT))
                        playerOption = 1;
                    break;
                case 1:
                    if(KeyManager.isPressed(KeyManager.LEFT))
                        playerOption = 0;
                    else if(KeyManager.isPressed(KeyManager.RIGHT))
                        playerOption = 2;
                    break;
                case 2:
                    if(KeyManager.isPressed(KeyManager.LEFT))
                        playerOption = 1;
                    else if(KeyManager.isPressed(KeyManager.RIGHT))
                        playerOption = 3;
                    break;
                case 3:
                    if(KeyManager.isPressed(KeyManager.LEFT))
                        playerOption = 2;
                    else if(KeyManager.isPressed(KeyManager.RIGHT))
                        playerOption = 4;
                    break;
                case 4:
                    if(KeyManager.isPressed(KeyManager.LEFT))
                        playerOption = 3;
                    else if(KeyManager.isPressed(KeyManager.RIGHT))
                        playerOption = 0;
                    break;  
            }
        }
        
        
        if(KeyManager.isPressed(KeyManager.RIGHT) && currentOption == 4)
            currentOption = 5;
        else if(KeyManager.isPressed(KeyManager.LEFT) && currentOption == 5)
            currentOption = 4;
        else if (KeyManager.isPressed(KeyManager.DOWN) && currentOption < options.length-1){
            currentOption++;
        }
        else if (KeyManager.isPressed(KeyManager.UP) && currentOption > 0){
            currentOption--;
        }
        else if (KeyManager.isPressed(KeyManager.ENTER)){
            selectOption();
        }
    }
    
    private void selectOption(){
        if (currentOption == 4){
            saveSettings();
            gsm.changeState(new MenuState(gsm));
        }
        else if (currentOption == 5){
            gsm.changeState(new MenuState(gsm));
        }
    }
    
    @Override
    public void exit(){
        
    }
    
    @Override
    public int getStateID() {
        return SETTINGS_STATE;
    }
    
    private void saveSettings(){
        try {
            File file = new File(".settings");
            PrintWriter pw = new PrintWriter(file);
            pw.println("language="+langs[languageOption]);
            pw.println("player="+playerNames[playerOption]);
            pw.println("sfx="+(sound[sfxOption]==LanguageManager.getInstance().get("encendido")?1:0));
            pw.println("music="+(sound[musicOption]==LanguageManager.getInstance().get("encendido")?1:0));
            pw.close();
            SettingsManager.getInstance().loadSettings();
            LanguageManager.getInstance().reloadLanguage();
            TextureManager.reloadPlayer();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }
}

