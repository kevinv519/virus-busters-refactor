package app.main;

import app.manager.GameStateManager;
import app.manager.KeyManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class GameApp extends Application {
    public static final int PANEL_WIDTH = 640;
    public static final int PANEL_HEIGHT = 640;

    @Override
    public void start(Stage primaryStage) throws Exception {
        var root = new Group();
        var canvas = new Canvas(PANEL_WIDTH, PANEL_HEIGHT);
        var scene = new Scene(root);

        root.getChildren().add(canvas);
        scene.setOnKeyPressed(keyEvent -> KeyManager.setKey(keyEvent.getCode(), true));
        scene.setOnKeyReleased(keyEvent -> KeyManager.setKey(keyEvent.getCode(), false));

        primaryStage.setTitle("Hola");
        primaryStage.setResizable(false);
        primaryStage.setWidth(PANEL_WIDTH);
        primaryStage.setHeight(PANEL_HEIGHT);
        primaryStage.setScene(scene);

        var graphicsContext = canvas.getGraphicsContext2D();
        var gameStateManager = new GameStateManager();

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                gameStateManager.update();
                KeyManager.update();
                gameStateManager.draw(graphicsContext);
            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
