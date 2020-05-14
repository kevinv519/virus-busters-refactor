package app.gamestate;

import app.main.GamePanel;
import app.manager.GameStateManager;
import app.manager.KeyManager;
import app.manager.LanguageManager;
import app.manager.TextManager;
import app.manager.TextureManager;
import java.awt.Color;
import java.awt.Graphics2D;


public class PauseState extends GameState {
    
    private final int PAUSE_STATE = 4;
    private int currentOption;
    
    private final String options[] = {
        LanguageManager.getInstance().get("continue"),
        LanguageManager.getInstance().get("salir")
    };
    
    public PauseState(GameStateManager gsm) {
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
        g.drawImage(TextureManager.pausa, 0, 0,GamePanel.PANEL_WIDTH,GamePanel.PANEL_HEIGHT, null);
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
        return PAUSE_STATE;
    }
    
    private void selectOption(){
        switch (currentOption) {
            case 0:
                gsm.setPaused(false);
                break;
            case 1:
                gsm.changeState(new MenuState(gsm));
                gsm.setPaused(false);
                break;
            default:
                break;
        }
    }
}
