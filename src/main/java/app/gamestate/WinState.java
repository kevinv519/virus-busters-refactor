package app.gamestate;

import app.main.GamePanel;
import app.manager.GameStateManager;
import app.manager.KeyManager;
import app.manager.LanguageManager;
import app.manager.TextManager;
import app.manager.TextureManager;
import java.awt.Color;
import java.awt.Graphics2D;


public class WinState extends GameState{

    private final int WIN_STATE = 6;
    private final int usbs;
    private final int coins;
    private final int ticks;
    private final int minutes;
    private final int seconds;
    private final int remainingLife;
    private final String time;
    private int score;
    
    private final String options[] = {
        LanguageManager.getInstance().get("save"),
        LanguageManager.getInstance().get("cancel")
    };
    
    private int currentOption;
    
    private final String title = LanguageManager.getInstance().get("win");
    
    public WinState(GameStateManager gsm, int ticks, int usbs, int coins, int life) {
        super(gsm);
        this.usbs = usbs;
        this.coins = coins;
        this.remainingLife = life;
        this.ticks = ticks;
        minutes = (int) (ticks/3600);
        seconds = (int) ((ticks/60)%60);
        time = (minutes < 10? "0"+minutes: String.valueOf(minutes)) + ":"+ (seconds < 10? "0"+seconds: String.valueOf(seconds));
    }

    @Override
    public void init() {
        currentOption = 0;
        score = calcularPuntos();
    }

    @Override
    public void update() {
        handleEvents();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(TextureManager.win, 0, 0, null);
        g.setColor(Color.BLACK);
        g.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 64));
        g.drawString(title, TextManager.centerPosX(title, g, GamePanel.PANEL_WIDTH), 130);
        g.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 54));
        g.drawImage(TextureManager.WIN_usb, 190, 160, null);
        g.drawString(String.valueOf(usbs), 330, 210);
        g.drawImage(TextureManager.WIN_coin, 190, 230, null);
        g.drawString(String.valueOf(coins), 330, 280);
        g.drawImage(TextureManager.WIN_life, 190, 300, null);
        g.drawString(String.valueOf(remainingLife), 330, 350);
        g.drawImage(TextureManager.WIN_clock, 190, 370, null);
        g.drawString(time, 330, 420);
        g.fillRect(190, 430, 270, 5);
        g.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 40));
        g.drawString("Total=", 190, 470);
        g.drawString(String.valueOf(calcularPuntos()), 330, 470);
        for (int i = 0; i < options.length; i++) {
            if (i==currentOption){
                g.setColor(Color.RED);
                g.drawImage(TextureManager.arrow, 135+175*i, 505, 30, 30, null);
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawString(options[i], 170+170*i, 530);
        }
    }

    @Override
    public void handleEvents() {
        if (KeyManager.isPressed(KeyManager.RIGHT) && currentOption < options.length-1){
            currentOption++;
        }
        else if (KeyManager.isPressed(KeyManager.LEFT) && currentOption > 0){
            currentOption--;
        }
        else if (KeyManager.isPressed(KeyManager.ENTER)){
            selectOption();
        }
    }

    @Override
    public void exit() {
        
    }

    @Override
    public int getStateID() {
        return WIN_STATE;
    }
    
    
    private int calcularPuntos(){
        int puntaje = 0;
        puntaje += usbs * 100;
        puntaje += remainingLife * 10;
        puntaje += coins*20;
        if (ticks <= 18000){
            puntaje += (18000-ticks)/8;
        }
        return puntaje;
    }
    
    private void selectOption(){
        if (currentOption == 0){
            new BDialog(score);
            gsm.changeState(new MenuState(gsm));
        }
        else if (currentOption == 1){
            gsm.changeState(new MenuState(gsm));
        }
    }
}
