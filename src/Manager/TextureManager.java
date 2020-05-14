package Manager;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Kevin
 */
public class TextureManager {
    public static BufferedImage logo = load("logo.png");
    public static BufferedImage menu = load("Menu.png");
    public static BufferedImage pausa = load("Pausa.png");
    public static BufferedImage arrow = load("select40.png");
    public static BufferedImage spritePlayer[][] = loadSpriteSheet(SettingsManager.getInstance().getPlayer(), 64, 64);
    public static BufferedImage spritePlayerBullet[][] = loadSpriteSheet("Bullet.png", 64, 64);
    public static BufferedImage spriteEnemy[][] = loadSpriteSheet("enemy1.png", 64, 64);
    public static BufferedImage spriteEnemyBullet[][] = loadSpriteSheet("enemy1bullet.png", 64, 64);
    public static BufferedImage spriteBoss[][] = loadSpriteSheet("BossSprite1.png", 128, 128);
    public static BufferedImage spriteBossBullet[][] = loadSpriteSheet("bossbullet.png", 64, 64);
    public static BufferedImage xplodeBoss[] = loadSpriteSheet("bossxplode.png", 128, 128)[0];
    public static BufferedImage level1 = load("Labo.png");
    public static BufferedImage level2 = load("Aula.png");
    public static BufferedImage level3 = load("Magna.png");
    public static BufferedImage levelFinal = load("Final.png");
    public static BufferedImage USB[] = loadSpriteSheet("usb.png", 64, 64)[0];
    public static BufferedImage HUD_bullet = load("HUD-gun.png");
    public static BufferedImage HUD_USB = load("HUD-usb.png");
    public static BufferedImage coin[] = loadSpriteSheet("coin1.png", 32, 32)[0];
    public static BufferedImage HUD_coin = load("HUD-coin.png");
    public static BufferedImage win = load("win.png");
    public static BufferedImage WIN_clock = load("WIN-clock.png");
    public static BufferedImage WIN_coin = load("WIN-coin.png");
    public static BufferedImage WIN_usb = load("WIN-usb.png");
    public static BufferedImage WIN_life = load("WIN-life.png");
    public static BufferedImage b1 = load("b.png");
    public static BufferedImage b2 = load("b2.png");
    public static BufferedImage b3 = load("b3.png");
    public static BufferedImage help = load("help.png");
    
    private static BufferedImage load(String filename){
        BufferedImage temp = null;
        try {
            temp = ImageIO.read(TextureManager.class.getClassLoader().getResourceAsStream(filename));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return temp;
    }
    
    public static BufferedImage[][] loadSpriteSheet(String filename, int width, int height){
        BufferedImage temp[][];
        try {
            BufferedImage spritesheet = ImageIO.read(TextureManager.class.getClassLoader().getResourceAsStream(filename));
            int rows = spritesheet.getHeight()/ height;
            int cols = spritesheet.getWidth()/ width;
            temp = new BufferedImage[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    temp[i][j] = spritesheet.getSubimage(j*width, i*height, width, height);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            temp = null;
        }
        return temp;
    }
    
    public static void reloadPlayer(){
        spritePlayer = loadSpriteSheet(SettingsManager.getInstance().getPlayer(), 64, 64);
    }
}
