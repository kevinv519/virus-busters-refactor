package app.gamestate;

import app.main.GamePanel;
import app.manager.GameStateManager;
import app.manager.KeyManager;
import app.manager.LanguageManager;
import app.manager.TextManager;
import app.manager.TextureManager;
import java.awt.Color;
import java.awt.Graphics2D;
/**
 * @author Kevin
 */
public class MenuState extends GameState{
    
    private final int MENU_STATE = 1;
    
    private int currentOption;
    private final String options[] = {
        LanguageManager.getInstance().get("iniciar"),
        LanguageManager.getInstance().get("ayuda"),
        LanguageManager.getInstance().get("puntaje"),
        LanguageManager.getInstance().get("settings"),
        LanguageManager.getInstance().get("salir")
    };

    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        currentOption = 0;
    }

    @Override
    public void update() {
        handleEvents();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(TextureManager.menu, 0, 0,GamePanel.PANEL_WIDTH,GamePanel.PANEL_HEIGHT, null);
        g.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 50));
        for (int i = 0; i < options.length; i++) {
            if (currentOption == i){
                g.setColor(Color.RED);
                g.drawImage(TextureManager.arrow, 140, 190 + currentOption*70, 40, 40, null);
            }
            else{
                g.setColor(Color.BLACK);
            }
            g.drawString(options[i], TextManager.centerPosX(options[i], g, GamePanel.PANEL_WIDTH), 230+70*i);
        }
    }

    @Override
    public void handleEvents() {
        if (KeyManager.isPressed(KeyManager.DOWN) && currentOption < options.length-1){
            currentOption++;
        }
        else if (KeyManager.isPressed(KeyManager.UP) && currentOption > 0){
            currentOption--;
        }
        else if (KeyManager.isPressed(KeyManager.ENTER)){
            selectOption();
        }
    }

    @Override
    public void exit() {
        
    }

    @Override
    public int getStateID() {
        return MENU_STATE;
    }
    
    private void selectOption(){
        switch (currentOption) {
            case 0:
                gsm.changeState(new PlayState(gsm));
                break;
            case 1:
                gsm.pushState(new HelpState(gsm));
                break;
            case 2:
                gsm.pushState(new ScoreState(gsm));
                break;
            case 3:
                gsm.changeState(new SettingsState(gsm));
                break;
            case 4:
                System.exit(0);
                break;
            default:
                break;
        }
    }

}
