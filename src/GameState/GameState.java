package GameState;

import Manager.GameStateManager;
import java.awt.Graphics2D;

/**
 * @author Kevin
 */
public abstract class GameState {
    protected GameStateManager gsm;
    
    public GameState(GameStateManager gsm){
        this.gsm = gsm;
    }
    
    public abstract void init();
    public abstract void update();
    public abstract void draw(Graphics2D g);
    public abstract void handleEvents();
    public abstract void exit();
    public abstract int getStateID();
}
