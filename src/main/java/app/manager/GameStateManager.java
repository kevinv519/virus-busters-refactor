package app.manager;

import app.gamestate.GameState;
import app.gamestate.HelpState;
import app.gamestate.IntroState;
import app.gamestate.MenuState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameStateManager {
    private GameState[] gameStates;

    private static final int numGameStates = 4;
    private int currentState;

    public static final int STATE_INTRO = 0;
    public static final int STATE_MENU = 1;
    public static final int STATE_PLAY = 2;
    public static final int STATE_HELP = 3;

    public GameStateManager() {
        gameStates = new GameState[numGameStates];
        currentState = STATE_INTRO;
        loadState(currentState);
    }

    private void loadState(int state) {
        switch (state) {
            case STATE_INTRO:
                gameStates[state] = new IntroState(this);
                break;
            case STATE_MENU:
                gameStates[state] = new MenuState(this);
                break;
            case STATE_HELP:
                gameStates[state] = new HelpState(this);
                break;
        }
    }

    private void unloadState(int state) {
        gameStates[state] = null;
    }

    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }

    public void draw(GraphicsContext graphics) {
        if (gameStates[currentState] != null) gameStates[currentState].draw(graphics);
        else {
            graphics.setFill(Color.BLACK);
            graphics.fillRect(0, 0, 640, 640);
        }
    }

    public void update() {
        if (gameStates[currentState] != null) gameStates[currentState].update();
    }
}
