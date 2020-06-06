package app.gamestate;

import app.manager.GameStateManager;
import app.manager.KeyManager;
import app.manager.LanguageManager;
import app.manager.TextManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class HelpState extends GameState {

    private final String[] labelsControls = {
            LanguageManager.getInstance().get("help_attack"),
            LanguageManager.getInstance().get("help_run"),
            LanguageManager.getInstance().get("help_pause"),
            LanguageManager.getInstance().get("help_move"),
    };
    private final String labelExit = LanguageManager.getInstance().get("help_exit");
    private final String labelHelp = LanguageManager.getInstance().get("menu_help");

    private Font pusab40;
    private Font pusab50;

    public HelpState(GameStateManager gameStateManager) {
        super(gameStateManager);
        init();
    }

    @Override
    public void init() {
        pusab40 = TextManager.loadFont(TextManager.PUSAB, 40);
        pusab50 = TextManager.loadFont(TextManager.PUSAB, 50);
    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.drawImage(textureManager.getImage("state-help"), 0, 0);
        graphics.setFill(Color.BLACK);

        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.setFont(pusab50);
        graphics.fillText(labelHelp, 320, 120);

        graphics.setFont(pusab40);
        graphics.setTextAlign(TextAlignment.LEFT);
        graphics.fillText(labelsControls[0], 330, 210);
        graphics.fillText(labelsControls[1], 330, 280);
        graphics.fillText(labelsControls[2], 330, 355);
        graphics.fillText(labelsControls[3], 330, 460);

        graphics.setFont(pusab50);
        graphics.setFill(Color.RED);
        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.fillText(labelExit, 320, 545);
        graphics.drawImage(textureManager.getImage("arrow-right"), 180, 512);
    }

    @Override
    public void handleInput() {
        if (KeyManager.isPressed(KeyManager.K_ENTER)) {
            gameStateManager.setState(GameStateManager.STATE_MENU);
        }
    }
}