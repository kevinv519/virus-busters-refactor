package app.level;

import app.entity.Bullet;
import app.entity.EnemyController;
import app.entity.Player;
import app.entity.USB;
import app.gamestate.ContinueState;
import app.gamestate.LDialog;
import app.gamestate.MenuState;
import app.manager.AudioManager;
import app.manager.GameStateManager;
import app.manager.KeyManager;
import app.manager.LevelManager;
import app.tilemap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public abstract class Level implements Runnable{
    protected BufferedImage fondo;
    
    protected Player player;
    
    private final GameStateManager gsm;
    
    protected boolean completed;
    
    protected TileMap tm;
    
    protected int MAX_ENEMIES;
    
    EnemyController enemies;
    
    protected boolean running;
    protected Thread thread;
    
    protected USB usb;
    
    private final Random r = new Random();
    
    ScheduledExecutorService enemyService;
        
    public Level(GameStateManager gsm){
        this.gsm = gsm;
        completed = false;
        enemies = new EnemyController();
        AudioManager.getInstance().volume(AudioManager.MUSIC, -5.0f);
        AudioManager.getInstance().volume(AudioManager.MUSIC_FINAL, -5.0f);
        AudioManager.getInstance().volume(AudioManager.PLAYER_SHOOT, -5.0f);
    }
    
    public void loadEnemies(int camX, int camY){
        enemies.loadEnemies(camX, camY, MAX_ENEMIES, tm, player);
        player.getBulletController().setTarget(enemies.getEnemies());
        usb.setPosition(camX, camY);
        enemyService  = Executors.newSingleThreadScheduledExecutor();
        enemyService.scheduleAtFixedRate(new EnemyTask(enemies), 600, 3500, TimeUnit.MILLISECONDS);
    }
    
    public void addBossEnemy(int camX, int camY){
        enemies.loadBossEnemy(camX, camY, tm, player);
    }
    
    public void setPlayer(Player player){
        this.player = player;
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public void handleEvents(){
        if(KeyManager.isPressed(KeyManager.ESCAPE)){
            running = false;
            enemyService.shutdown();
            gsm.setPaused(true);
        }
        
        player.setSpeed(KeyManager.isPressed(KeyManager.R) || KeyManager.isDown(KeyManager.R));
        player.setDelay(KeyManager.isDown(KeyManager.R));
        player.setShooting(KeyManager.isPressed(KeyManager.A) || KeyManager.isDown(KeyManager.A));
        
        if (KeyManager.isPressed(KeyManager.DOWN) || KeyManager.isDown(KeyManager.DOWN)){
            player.setDown();
            player.setMoving(true);
        }
        else if (KeyManager.isPressed(KeyManager.LEFT) || KeyManager.isDown(KeyManager.LEFT)){
            player.setLeft();
            player.setMoving(true);
        }
        else if (KeyManager.isPressed(KeyManager.RIGHT) || KeyManager.isDown(KeyManager.RIGHT)){
            player.setRight();
            player.setMoving(true);
        }
        else if (KeyManager.isPressed(KeyManager.UP) || KeyManager.isDown(KeyManager.UP)){
            player.setUp();
            player.setMoving(true);
        }
        else{
            player.setMoving(false);
        }
    }
    
    
    //COIN
    public void update(){
        if(player.getLife() <=0){
            running = false;
            enemyService.shutdown();
            if(LevelManager.getCoins() >= 10){
                gsm.pushState(new ContinueState(gsm));
                gsm.setInContinue(true);
            }
            else{
                new LDialog();
                gsm.changeState(new MenuState(gsm));
                exit();
                return;
            }
        }

        if (!gsm.isPaused() && !gsm.isInContinue() && enemyService.isShutdown() && !running){
            running = true;
            thread = new Thread(this);
            thread.start();
            enemyService  = Executors.newSingleThreadScheduledExecutor();
            enemyService.scheduleAtFixedRate(new EnemyTask(enemies), 600, 3500, TimeUnit.MILLISECONDS);
        }
        handleEvents();
        player.update();
        enemies.update(LevelManager.camMoving, player.getSpeed());
        
        if(!enemies.getCoinControllers().isEmpty()){
            enemies.getCoinControllers().forEach((controller)->{
                if(!controller.getCoins().isEmpty()){
                    controller.getCoins().forEach((coin)->{
                        if(player.intersects(coin)){
                            coin.taken();
                            LevelManager.addCoin();
                        }
                    });
                }
            });
        }
        
        if(enemies.getEnemies().isEmpty()){
            enemyService.shutdown();
            if (!usb.isTaken()){
                if (player.intersects(usb)){
                    LevelManager.addUsb();
                    usb.setTaken();
                    completed = true;
                }
            }
        }
    }
    
    public void draw(Graphics2D g){
        if(enemies.getEnemies().isEmpty() && !usb.isTaken()){
            usb.draw(g);
        }
        usb.redraw(LevelManager.camMoving, player.getSpeed());
        enemies.draw(g);
        if(player.getDirection()==1){
            player.draw(g);
            player.getBulletController().draw(g);
        }
        else{
            player.getBulletController().draw(g);
            player.draw(g);
        }
    }
    
    public void setRedraw(){
        enemies.redraw(LevelManager.camMoving, player.getSpeed());
    }
    public final TileMap getTileMap(){
        return tm;
    }
    
    public BufferedImage getFondo(){
        return fondo;
    }
    
    public int getWidth(){
        return tm.getMapWidth();
    }
    
    public int getHeight(){
        return tm.getMapHeight();
    }
    
    public boolean isCompleted(){
        return completed;
    }
    
    @Override
    public void run() {
        int c = 0;
        while(running){
            synchronized(this){ // hay que sincronizar el objeto
                if (KeyManager.isDown(KeyManager.A)){
                    c++;
                    if(c == 2){
                        AudioManager.getInstance().stop(AudioManager.PLAYER_SHOOT);
                        AudioManager.getInstance().play(AudioManager.PLAYER_SHOOT);
                         c = 1;
                    }
                    player.getBulletController().addBullet(
                            new Bullet(
                                    tm,
                                    1,
                                    player.getPosX()+r.nextInt(8),
                                    player.getPosY()+r.nextInt(7),
                                    player.getPosRX(),
                                    player.getPosRY(),
                                    player.getRowTile(),
                                    player.getColTile(),
                                    player.getDirection(), 12, 1
                            )
                    );
                }
            }
        }
    }
    
    public void exit(){
        running = false;
        AudioManager.getInstance().stop(AudioManager.MUSIC);
        AudioManager.getInstance().stop(AudioManager.MUSIC_FINAL);
        enemyService.shutdown();
    }
}
