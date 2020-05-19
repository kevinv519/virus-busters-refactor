package app.gamestate;

import app.manager.GameStateManager;
import app.manager.TextureManager;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameState {
    protected GameStateManager gameStateManager;
    protected TextureManager textureManager;

    public GameState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.textureManager = TextureManager.getInstance();
    }

    public abstract void init();
    public abstract void update();
    public abstract void draw(GraphicsContext graphics);
    public abstract void handleInput();

}
