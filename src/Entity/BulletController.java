
package Entity;

import TileMap.TileMap;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gama
 */
public class BulletController extends Thread{
    private final List<Bullet> b = new ArrayList<>();
    Bullet tempBullet;
    TileMap tm;
    private final int bulletDelay;
    
    private List<Enemy> targets;
    private ShooterEntity target;
    
    public BulletController(int bulletDelay){
        this.bulletDelay = bulletDelay;
    }
    
    public void setTarget(List<Enemy> targets){
        this.targets = targets;
    }
    
    public void setTarget(ShooterEntity target){
        this.target = target;
    }
    
    public void update(int dircam, int speed){
        for (int i = 0; i < b.size(); i++) {
            tempBullet = b.get(i);
            tempBullet.update();
            if(target != null)
                tempBullet.crash(target);
            else
                tempBullet.crash(targets);
            tempBullet.walk();
            tempBullet.redraw(dircam, speed);
            if(!tempBullet.moving){
                b.remove(tempBullet);
                tempBullet = null;
                System.gc();
            }
        }
    }
    
    public void draw(Graphics2D g){
        for (int i = 0; i < b.size(); i++) {
            tempBullet = b.get(i);
            tempBullet.draw(g);
        }
    }
    
    public void addBullet(Bullet aux){
        b.add(aux);
        try {
            Thread.sleep(bulletDelay*100);
        } catch (InterruptedException ex) {
            System.out.println("Bullet Controller failed");
        }
    }
    
    public void addBullete(Bullet aux){
        b.add(aux);
    }
    
    public void dropBullet(Bullet aux){
        b.remove(aux);
    }
    
}
