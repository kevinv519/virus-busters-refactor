package app.manager;


import javafx.scene.input.KeyCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Kevin
 */
public class KeyManager {
    private static final Logger logger = LogManager.getLogger(KeyManager.class);
    private static final int NUM_KEYS = 10;
    private static final boolean[] keyState = new boolean[NUM_KEYS];
    private static final boolean[] previousKeyState = new boolean[NUM_KEYS];

    public static final int K_UP = 0;
    public static final int K_LEFT = 1;
    public static final int K_DOWN = 2;
    public static final int K_RIGHT = 3;
    public static final int K_SPACE = 4;
    public static final int K_ENTER = 5;
    public static final int K_ESCAPE = 6;
    public static final int K_F1 = 7;
    public static final int K_R = 8;
    public static final int K_A = 9;

    public static void setKey(KeyCode i, boolean b) {
        switch (i) {
            case UP:
                keyState[K_UP] = b;
                break;
            case LEFT:
                keyState[K_LEFT] = b;
                break;
            case DOWN:
                keyState[K_DOWN] = b;
                break;
            case RIGHT:
                keyState[K_RIGHT] = b;
                break;
            case SPACE:
                keyState[K_SPACE] = b;
                break;
            case ENTER:
                keyState[K_ENTER] = b;
                break;
            case ESCAPE:
                keyState[K_ESCAPE] = b;
                break;
            case F1:
                keyState[K_F1] = b;
                break;
            case R:
                keyState[K_R] = b;
                break;
            case A:
                keyState[K_A] = b;
                break;
            default:
                break;
        }
    }

    public static void update() {
        System.arraycopy(keyState, 0, previousKeyState, 0, NUM_KEYS);
    }

    public static boolean isPressed(int i) {
        logger.debug("keyState[" + i + "] = " + keyState[i] + "\npreviousKeyState[" + i + "] = " + previousKeyState[i]);
        return keyState[i] && !previousKeyState[i];
    }
    
    public static boolean isDown(int i){
        return keyState[i];
    }
    
}
