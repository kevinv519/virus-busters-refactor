package app.main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameApp extends Application {
    public static final int PANEL_WIDTH = 640;
    public static final int PANEL_HEIGHT = 640;

    @Override
    public void start(Stage primaryStage) throws Exception {
        var root = new Group();
        var canvas = new Canvas(PANEL_WIDTH, PANEL_HEIGHT);
        root.getChildren().add(canvas);

        primaryStage.setTitle("Hola");
        primaryStage.setResizable(false);
        primaryStage.setWidth(PANEL_WIDTH);
        primaryStage.setHeight(PANEL_HEIGHT);
        primaryStage.setScene(new Scene(root));

        var graphicsContext = canvas.getGraphicsContext2D();

        final var startTime = System.nanoTime();
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                var elapsedTime = System.nanoTime() - startTime;
                System.out.println(elapsedTime);
            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
