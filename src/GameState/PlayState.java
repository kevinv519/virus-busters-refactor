package GameState;

import Manager.GameStateManager;
import Manager.LevelManager;
import java.awt.Graphics2D;

/**
 * @author Kevin
 */
public class PlayState extends GameState{

    private final int PLAY_STATE = 2;
    private LevelManager lm;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        
    }

    @Override
    public void init() {
        System.out.println("Iniciando Play State");
        lm = new LevelManager(gsm);
    }

    @Override
    public void update() {
            handleEvents();
    }

    @Override
    public void draw(Graphics2D g) {
        lm.draw(g);
    }

    @Override
    public void handleEvents() {
        lm.update();
    }

    @Override
    public void exit() {
        lm.exit();
    }

    @Override
    public int getStateID() {
        return PLAY_STATE;
    }
    
}
