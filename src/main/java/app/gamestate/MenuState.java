package app.gamestate;

import app.enums.GameStates;
import app.manager.*;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class MenuState extends GameStateBase {

    private final String[] labels = {
            LanguageManager.INSTANCE.get("menu_start"),
            LanguageManager.INSTANCE.get("menu_help"),
            LanguageManager.INSTANCE.get("menu_score"),
            LanguageManager.INSTANCE.get("menu_settings"),
            LanguageManager.INSTANCE.get("menu_exit"),
    };

    private Font pusabFont;
    private int currentOption;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        initialize();
    }

    @Override
    public void initialize() {
        pusabFont = TextManager.loadFont(TextManager.PUSAB, 50);
        currentOption = 0;
    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.drawImage(TextureManager.INSTANCE.getImage("state-menu"), 0, 0);

        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.setFont(pusabFont);
        for (int i = 0; i < labels.length; i++) {
            if (currentOption == i) {
                graphics.setFill(Color.RED);
                graphics.drawImage(TextureManager.INSTANCE.getImage("arrow-right"), 150, 182 + i * 70);
            } else {
                graphics.setFill(Color.BLACK);
            }
            graphics.fillText(labels[i], 320, 215 + 70 * i);
        }
    }

    @Override
    public void handleInput() {
        if (KeyManager.isPressed(KeyManager.K_DOWN)) {
            currentOption++;
        } else if (KeyManager.isPressed(KeyManager.K_UP)) {
            currentOption--;
        } else if (KeyManager.isPressed(KeyManager.K_ENTER)) {
            selectOption();
        }

        verifyCurrentOption();
    }

    private void verifyCurrentOption() {
        if (currentOption == labels.length) {
            currentOption = 0;
        }
        else if (currentOption == -1) {
            currentOption = labels.length - 1;
        }
    }

    private void selectOption() {
        switch (currentOption) {
            case 1:
                getGameStateManager().pushState(GameStates.HELP);
                break;
            case 4:
                Platform.exit();
                System.exit(0);
                break;
        }
    }
}
