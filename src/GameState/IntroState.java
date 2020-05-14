package GameState;

import Main.GamePanel;
import Manager.GameStateManager;
import Manager.KeyManager;
import Manager.LanguageManager;
import Manager.TextureManager;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author Kevin
 */
public class IntroState extends GameState {
    
    private final int INTRO_STATE = 0;
    
    private int alpha;
    private int ticks;

    private final int FADE_IN = 60;
    private final int LENGTH = 60;
    private final int FADE_OUT = 60;
    
    private String lblGame;
    
    public IntroState(GameStateManager gsm) {
        super(gsm);
    }
    
    @Override
    public void init(){
        System.out.println("Iniciando IntroState");
        ticks = 0;
        lblGame = LanguageManager.getInstance().get("nombre_juego");
    }
    
    @Override
    public void update(){
        handleEvents();
        ticks++;
        if(ticks < FADE_IN) {
            alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
            if(alpha < 0) alpha = 0;
        }
        if(ticks > FADE_IN + LENGTH) {
            alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
            if(alpha > 255){
                alpha = 255;
            }
        }
        if(ticks > FADE_IN + LENGTH + FADE_OUT) {
            gsm.changeState(new MenuState(gsm));
        }
    }
    
    @Override
    public void draw(Graphics2D g){
        try {
            g.setColor(new Color(255, 255, 255));
            g.fillRect(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
            g.drawImage(
                    TextureManager.logo, 
                    0,
                    0,
                    null
            );
            g.setColor(new Color(0, 0, 0, alpha));
            g.fillRect(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    @Override
    public void exit(){
        System.out.println("Finalizando IntroState");
    }
    
    @Override
    public void handleEvents(){
        if (KeyManager.isPressed(KeyManager.ENTER)) {
            gsm.changeState(new MenuState(gsm));
        }
    }
    
    @Override
    public int getStateID() {
        return INTRO_STATE;
    }
}
