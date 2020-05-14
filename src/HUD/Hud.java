package HUD;

import Main.GamePanel;
import Manager.LevelManager;
import Manager.TextManager;
import Manager.TextureManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


public class Hud {
    private final LevelManager lm;
    private final Font font;
    private final Color colors[] = {new Color(93,224,229), new Color(88,92,92), new Color(48,142,145)};
    
    private int minutes, seconds;
    private String time;
    
    public Hud(LevelManager lm){
        this.lm =  lm;
        font = TextManager.getFont(TextManager.BLOCKTOPIA, 32);
    }
    
    public void draw(Graphics2D g){
        g.setColor(colors[0]);
        g.fillRect(10, 10, 200, 20);
        g.setColor(colors[1]);
        g.fillRect(12, 12, 196, 16);
        g.setColor(colors[2]);
        g.fillRect(12, 12, (int)(194*((float)lm.getPlayer().getLife()/lm.getPlayer().getMaxLife())), 16);
        g.setColor(Color.WHITE);
        
        minutes = (int) (lm.getTicks()/3600);
        seconds = (int) ((lm.getTicks()/60)%60);
        time = (minutes < 10? "0"+minutes: String.valueOf(minutes)) + ":"+ (seconds < 10? "0"+seconds: String.valueOf(seconds));

        g.setFont(font.deriveFont(32));
        g.drawString(time, GamePanel.PANEL_WIDTH - 100, 30);

        g.drawImage(TextureManager.HUD_coin, 15, 28, 32, 36, null);
        g.drawString(String.valueOf(LevelManager.getCoins()), 48, 55);
        
        if(LevelManager.getUsbs() >0){
            for(int i = 1; i <= LevelManager.getUsbs(); i++){
                g.drawImage(TextureManager.HUD_USB, null, (32*i)-20, 60);
            }
        }
    }
}
