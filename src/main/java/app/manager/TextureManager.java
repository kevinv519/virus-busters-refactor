package app.manager;

import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * @author Kevin
 */
public class TextureManager {
    private final static Logger logger = LogManager.getLogger(TextureManager.class);
    private final static String PATH = "images/";

    private HashMap<String, Image> images;

    public TextureManager() {
        images = new HashMap<>();
        loadImage("logo.png", "logo");
        loadImage("state-menu.png", "state-menu");
        loadImage("state-pause.png", "state-pause");
        loadImage("state-win.png", "state-win");
        loadImage("state-help.png", "state-help");
        loadImage("arrow-right.png", "arrow-right");
        loadImage("sprite-bullet.png", "sprite-bullet");
        loadImage("sprite-enemy.png", "sprite-enemy");
        loadImage("sprite-enemy-bullet.png", "sprite-enemy-bullet");
        loadImage("sprite-boss.png", "sprite-boss");
        loadImage("sprite-boss-bullet.png", "sprite-boss-bullet");
        loadImage("sprite-boss-explosion.png", "sprite-boss-explosion");
        loadImage("sprite-usb.png", "usb");
        loadImage("sprite-coin.png", "sprite-coin");
        loadImage("level-lab.png", "level-lab");
        loadImage("level-classroom.png", "level-classroom");
        loadImage("level-magna.png", "level-magna");
        loadImage("level-final.png", "level-final");
        loadImage("hud-usb.png", "hud-usb");
        loadImage("hud-coin.png", "hud-coin");
        loadImage("win-clock.png", "win-clock");
        loadImage("win-coin.png", "win-coin");
        loadImage("win-usb.png", "win-usb");
        loadImage("win-life.png", "win-life");
        loadImage("button.png", "button");
        loadImage("button-over.png", "button-over");
        loadImage("button-pressed.png", "button-pressed");
    }

    public Image getImage(String key) {
        return images.getOrDefault(key, null);
    }

    public int countAvailableImages() {
        return images.size();
    }

    private void loadImage(String filename, String key) {
        try {
            var image = new Image(PATH + filename);
            images.put(key, image);
        } catch (Exception e) {
            logger.error("Error occurred while loading image: " + filename + ". More details:", e);
        }
    }
}
