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
public class HelpState extends GameState{
    
    private final int HELP_STATE = 10;
    
    private int currentOption;
    private final String options[] = {
        LanguageManager.getInstance().get("salir")
    };
    
    private final String controls[] = {
        //LanguageManager.getInstance().get("salir")
        LanguageManager.getInstance().get("shoot"),
        LanguageManager.getInstance().get("run"),
        LanguageManager.getInstance().get("pause"),
        LanguageManager.getInstance().get("moving")
    };

    public HelpState(GameStateManager gsm) {
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
        g.drawImage(TextureManager.help, 0, 0,GamePanel.PANEL_WIDTH,GamePanel.PANEL_HEIGHT, null);
        g.drawString(LanguageManager.getInstance().get("help"),
                TextManager.centerPosX(LanguageManager.getInstance().get("help"), g, GamePanel.PANEL_WIDTH),120);
        g.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 35));
        g.setColor(Color.BLACK);
        for (int i = 0; i < controls.length-1; i++) {
            g.drawString(controls[i], TextManager.centerPosX(controls[i], g, GamePanel.PANEL_WIDTH)+70, 210+75*i);
        }
        g.drawString(controls[3], TextManager.centerPosX(controls[3], g, GamePanel.PANEL_WIDTH)+80, 250+75*3);
        g.setColor(Color.red);
        g.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 50));
        g.drawImage(TextureManager.arrow, 140, 512, 40, 40, null);
        g.drawString(options[0], TextManager.centerPosX(options[0], g, GamePanel.PANEL_WIDTH), 545);
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
        return HELP_STATE;
    }
    
    private void selectOption(){
        switch (currentOption) {
            case 0:
                System.out.println("POP");
                gsm.popState();
                break;
            default:
                gsm.popState();
                break;
        }
    }

}
