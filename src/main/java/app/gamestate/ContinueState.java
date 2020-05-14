package app.gamestate;

import app.main.GamePanel;
import app.manager.GameStateManager;
import app.manager.KeyManager;
import app.manager.LanguageManager;
import app.manager.LevelManager;
import app.manager.TextManager;
import app.manager.TextureManager;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Gama
 */
public class ContinueState extends GameState{
    private final int CONTINUE_STATE = 6;
    private int currentOption;
    
    private final String options[] = {
        LanguageManager.getInstance().get("yes"), "no"
//        "Yes", "No"
//        LanguageManager.getInstance().get("settings"),
//        LanguageManager.getInstance().get("salir")
//         "Yes","No"
    };
    
    public ContinueState(GameStateManager gsm) {
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
        g.setColor(Color.BLACK);
        g.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 50));
        g.drawString(LanguageManager.getInstance().get("askcontinue"), TextManager.centerPosX(LanguageManager.getInstance().get("askcontinue"), g, GamePanel.PANEL_WIDTH), 220);
        g.drawImage(TextureManager.HUD_coin, 260, 240,45,45, null);
        g.drawString("x10", 310, 280);
        //g.drawString("Continue?", TextManager.centerPosX("Continue?", g, GamePanel.PANEL_WIDTH), 200);
        for (int i = 0; i < options.length; i++) {
            if (currentOption == i){
                g.setColor(Color.RED);
                g.drawImage(TextureManager.arrow, 140, 310 + currentOption*70, 40, 40, null);
            }
            else{
                g.setColor(Color.BLACK);
            }
            //g.setColor(Color.BLACK);
            g.drawString(options[i], TextManager.centerPosX(options[i], g, GamePanel.PANEL_WIDTH), 345+70*i);
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
        return CONTINUE_STATE;
    }
    
    private void selectOption(){
        switch (currentOption) {
            case 0:
                LevelManager.payCoins(10);
                LevelManager.player.setLife(150);
                gsm.setInContinue(false);
                gsm.popState();
                break;
            case 1:
                System.out.println("Lol");
                gsm.popState();
                gsm.changeState(new MenuState(gsm));
                gsm.setInContinue(false);
                break;
            case 3:
                System.exit(0);
            default:
                break;
        }
    }
}
