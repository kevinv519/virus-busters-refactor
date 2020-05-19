package app.gamestate;

import app.manager.GameStateManager;
import app.manager.KeyManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntroState extends GameState {
    private static final Logger logger = LogManager.getLogger(IntroState.class);

    private static final int FADE_IN = 80;
    private static final int FADE_OUT = 80;
    private static final int LENGTH = 80;

    private double alpha;
    private int ticks;

    public IntroState(GameStateManager gameStateManager) {
        super(gameStateManager);
        init();
    }

    @Override
    public void init() {
        logger.info("Entering IntroState");
        ticks = 0;
    }

    @Override
    public void update() {
        handleInput();
        ticks++;
        if(ticks < FADE_IN) {
            alpha = (1 - (1.0 * ticks / FADE_IN));
            if(alpha < 0) alpha = 0;
        }
        if(ticks > FADE_IN + LENGTH) {
            alpha = (1 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
            if(alpha > 1){
                alpha = 1.0;
            }
        }
        if(ticks > FADE_IN + LENGTH + FADE_OUT) {
            gameStateManager.setState(GameStateManager.STATE_MENU);
        }
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.drawImage(textureManager.getImage("logo"), 0, 0);
        graphics.setFill(Color.rgb(0, 0, 0, alpha));
        graphics.fillRect(0, 0, 640, 640);
    }

    @Override
    public void handleInput() {
        if (KeyManager.isPressed(KeyManager.K_ENTER)) {
            logger.info("User pressed enter. Skipping intro screen.");
            gameStateManager.setState(GameStateManager.STATE_MENU);
        }
    }
}
