package app.manager;

import app.entity.Player;
import app.gamestate.WinState;
import app.hud.Hud;
import app.level.Level;
import app.level.Level1;
import app.level.Level2;
import app.level.Level3;
import app.level.LevelFinal;
import app.main.GamePanel;
import java.awt.Graphics2D;

public class LevelManager {
    GameStateManager gsm;
    
    Level levels[];
    public static Player player;
    
    private boolean finished;
    
    private final Hud hud;
    
    private final int NUM_LEVELS = 4;
    private final int LEVEL1 = 0;
    private final int LEVEL2 = 1;
    private final int LEVEL3 = 2;
    private final int LEVELFINAL = 3;
    
    //Atributos para la camara
    private int MAP_SIZE_X, MAP_SIZE_Y;
    private int maxLimitX, maxLimitY, minLimitX, minLimitY;
    public static int camX, camY, pcX, pcY;
    public static int camMoving;
    
    private int currentLevel;
    private int ticks;
    
    public static int totalusb = 0;
    public static int coins = 0;
    
    public LevelManager(GameStateManager gsm){
        this.gsm = gsm;
        ticks = 0;
        totalusb =0;
        coins = 0;
        levels = new Level[NUM_LEVELS];
        loadLevel(LEVEL1);
        hud = new Hud(this);
        finished = false;
    }
    
    public static Player getPlayer(){
        return player;
    }
    
    private void loadLevel(int i){
        if (getLevel() != null){
            getLevel().exit();
            levels[currentLevel] = null;
        }
        currentLevel = i;
        switch (i){
            case LEVEL1:
                levels[i] = new Level1(gsm);
                break;
            case LEVEL2:
                levels[i] = new Level2(gsm);
                break;
            case LEVEL3:
                levels[i] = new Level3(gsm);
                break;
            case LEVELFINAL:
                levels[i] = new LevelFinal(gsm);
                getLevel().addBossEnemy(camX, camY);
                break;
        }
        player = new Player(levels[i].getTileMap());
        getLevel().setPlayer(player);
        setCameraBounds();
        setCameraPosition();
        levels[i].loadEnemies(camX, camY);
    }
    
    public void draw(Graphics2D g){
        g.drawImage(getLevel().getFondo(), -camX, -camY, levels[currentLevel].getWidth(), levels[currentLevel].getHeight(), null);
        getLevel().draw(g);
        hud.draw(g);
    }
    
    public void update(){
        if (!finished){
            ticks++;
        }
        else {
            gsm.popState();
            gsm.pushState(new WinState(gsm, ticks, totalusb, coins, player.getLife()));
            exit();
            return;
        }
        getLevel().update();
        setCameraPosition();
        if(((camX != pcX) || (camY != pcY))){ //la camara se mueve?
            if(pcY > camY){//arriba
                camMoving = 0;
            }
            if(pcY < camY){//abajo
                camMoving = 1;
            }
            if(pcX > camX){ //izquierda
                camMoving = 2;
            }
            if(pcX < camX){ //derecha
                camMoving = 3;
            }
        }else camMoving = 4; //no se mueve
        player.getBulletController().update(camMoving, player.getSpeed());
        getLevel().setRedraw();
        if (levels[currentLevel].isCompleted()){
            switch (currentLevel){
                case LEVEL1:
                    loadLevel(LEVEL2);
                    break;
                case LEVEL2 :
                    loadLevel(LEVEL3);
                    break;
                case LEVEL3:
                    loadLevel(LEVELFINAL);
                    break;
                case LEVELFINAL:
                    finished = true;
                    break;
            }
        }
    }
    
    private void setCameraBounds(){
        MAP_SIZE_X = getLevel().getWidth();
        MAP_SIZE_Y = getLevel().getHeight();
        maxLimitX = MAP_SIZE_X - GamePanel.PANEL_WIDTH;
        maxLimitY = MAP_SIZE_Y - GamePanel.PANEL_HEIGHT;
        minLimitX = minLimitY = 0;
        pcX = camX;
        pcY = camY;
    }
    
    private void setCameraPosition(){
        pcX = camX;
        pcY = camY;
        
        camX = player.getPosRX() - GamePanel.PANEL_WIDTH/2 + 32;
        camY = player.getPosRY() - GamePanel.PANEL_HEIGHT/2 + 32;
        
        if (camX > maxLimitX)
            camX = maxLimitX;
        else if (camX < minLimitX)
            camX = minLimitX;
        
        if (camY > maxLimitY)
            camY = maxLimitY;
        else if (camY < minLimitY)
            camY = minLimitY;
        
        if(player.getPosRX() > GamePanel.PANEL_WIDTH/2 - 32 && camX <maxLimitX)
            player.setPosX(GamePanel.PANEL_WIDTH/2 - 32);
        if(player.getPosRY() > GamePanel.PANEL_HEIGHT/2 - 32 && camY <maxLimitY)
            player.setPosY(GamePanel.PANEL_HEIGHT/2 - 32);
    }
    
    public void exit(){
        levels[currentLevel].exit();
    }
    
    private Level getLevel(){
        return levels[currentLevel];
    }
    
    public static void addUsb(){
        totalusb = totalusb + 1;
    }
    
    public static int getUsbs(){
        return totalusb;
    }
    
    public static void addCoin(){
        coins++;
    }
    public static void payCoins(int number){
        coins = coins - number;
    }
    
    public static int getCoins(){
        return coins;
    }
    
    public int getTicks(){
        return ticks;
    }
    
    public boolean isFinish(){
        return finished;
    }
}
