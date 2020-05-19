package app.gamestate;

import app.manager.GameStateManager;
import javafx.scene.canvas.GraphicsContext;

public class MenuState extends GameState {

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.drawImage(textureManager.getImage("state-menu"), 0, 0);
    }

    @Override
    public void handleInput() {

    }
}
