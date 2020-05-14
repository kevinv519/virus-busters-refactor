package Entity;

import Manager.AudioManager;
import Manager.LevelManager;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;


public class EnemyController {
    private List<Enemy> enemies;
    private List<CoinController> coinControllers;
    private Enemy tempEnemy;
    private TileMap tm;
    private final List<Integer> types;
    private int k;
    
    private int dir;
    private final Random r = new Random();
    
    public EnemyController(){
        enemies = new ArrayList<>();
        coinControllers = new ArrayList<>();
        types = new ArrayList<>();
        AudioManager.getInstance().volume(AudioManager.ENEMY_DEFEAT, 5.0f);
        AudioManager.getInstance().volume(AudioManager.ENEMY_SHOOT, -3.0f);
    }
    
    public void loadEnemies(int camX, int camY, int length, TileMap tm, Player player){
        this.tm = tm;
        for (int i = 0; i < length; i++) {
            enemies.add(new Enemy(this.tm));
            types.add(0);
        }
        enemies.forEach((enemy) -> {
            enemy.setMoving(true);
            enemy.setPosInicial(camX, camY);
            enemy.getBulletController().setTarget(player);
        });
    }
    
    public void loadBossEnemy(int camX, int camY, TileMap tm, Player player){
        Enemy e = new BossEnemy(tm);
        e.setMoving(true);
        e.setPosInicial(camX, camY);
        e.getBulletController().setTarget(player);
        enemies.add(e);
        types.add(2);
    }
    
    public List<Enemy> getEnemies(){
        return enemies;
    }
    
    public void removeAll(){
        AudioManager.getInstance().play(AudioManager.ENEMY_XPLODE);
        enemies.removeAll(enemies);
    }
    
    public void update(int dircam, int speed){
        for (ListIterator<Enemy> i = enemies.listIterator(); i.hasNext();) {
            tempEnemy = i.next();
            tempEnemy.update();
            tempEnemy.getBulletController().update(dircam, speed);
            if (tempEnemy.getLife()<=0){
                AudioManager.getInstance().stop(AudioManager.ENEMY_DEFEAT);
                AudioManager.getInstance().play(AudioManager.ENEMY_DEFEAT);
                tempEnemy.defeated();
            }
            if(!tempEnemy.isMoving()){
                AudioManager.getInstance().play(AudioManager.ENEMY_XPLODE);
                //COIN
                coinControllers.add(new CoinController(5+r.nextInt(11), tempEnemy.getPosRX(),
                tempEnemy.getPosRY(), LevelManager.camX, LevelManager.camY, tm));
                i.remove();
           }
        }
        //COIN
        if(!coinControllers.isEmpty()){
            for(ListIterator<CoinController> i = coinControllers.listIterator(); i.hasNext();){
                CoinController c = i.next();
                c.update(dircam, speed);
            }
        }
    }
    
    public void draw(Graphics2D g){
        enemies.forEach((enemy)->{
            enemy.draw(g);
            enemy.getBulletController().draw(g);
        });
        if(!coinControllers.isEmpty()){
           coinControllers.forEach((coin)->{
               coin.draw(g);
           });
        }
    }
    
    public void redraw(int dir, int speed){
        enemies.forEach((enemy)-> {
            enemy.redraw(dir, speed);
        });
    }
    
    public void automaticMovement(){
        k=0;
        for (ListIterator<Enemy> i = enemies.listIterator(); i.hasNext(); k++) {
            tempEnemy = i.next();
            dir = r.nextInt(4);
            tempEnemy.setShooting(true);
            for (int j = 0; j < 4; j++) {
                    AudioManager.getInstance().play(AudioManager.ENEMY_SHOOT);
                    tempEnemy.getBulletController().addBullete(new Bullet(tm, types.get(k),tempEnemy.getPosX()+r.nextInt(8), tempEnemy.getPosY()+r.nextInt(7),
                        tempEnemy.getPosRX(),tempEnemy.getPosRY(),tempEnemy.getRowTile(),tempEnemy.getColTile(), j, j==dir?4:2, 1));
            }
            tempEnemy.setShooting(false);
            switch(dir){
                case 0:
                    tempEnemy.setUp();
                    break;
                case 1:
                    tempEnemy.setDown();
                    break;
                case 2:
                    tempEnemy.setLeft();
                    break;
                case 3:
                    tempEnemy.setRight();
                    break;
                default:
                    tempEnemy.setUp();
                    break;
            }
        }
    }
    
    public List<CoinController> getCoinControllers(){
        return this.coinControllers;
    }
}
