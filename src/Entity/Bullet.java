package Entity;

import Manager.AudioManager;
import Manager.TextureManager;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author Gama
 */
public class Bullet extends ShooterEntity{
    
    private boolean hit = true;
    private int damage = 1;
    private BufferedImage sprite[][];
    
//    AudioManager bulletHit = new AudioManager("SFX/hit.wav");

    public Bullet(TileMap tm, int type, int posX, int posY, int posRX, int posRY,
            int rowtile, int coltile, int dir, int sp, int damage){
        super(tm);
        width = 64;
        height = 64;
        cwidth = cheight = 46;
        bulletPreferences (type, damage);
//        if (type == 2)
//            sprite = TextureManager.spriteBossBullet;
//        else if (type == 1)
//            sprite = TextureManager.spritePlayerBullet;
//        else
//            sprite = TextureManager.spriteEnemyBullet;
        
        downSprites = sprite[DOWN];
        leftSprites = sprite[LEFT];
        rightSprites = sprite[RIGHT];
        upSprites = sprite[UP];

        moving = true;
        
        speed = sp;
//        if (type == 2)
//            this.damage = 5;
//        else if (type == 0)
//            this.damage = damage;
//        else
//            this.damage = 1;
        delay = 3;
        animation.setFrames(downSprites);
        animation.setDelay(delay);

        this.posX = posX;
        this.posY = posY;
        this.posRX = posRX;
        this.posRY = posRY;
        this.rowTile = rowtile;
        this.colTile = coltile;

        dispCenter[0][0] = 32;
        dispCenter[0][1] = 54;
        dispCenter[1][0] = 32;
        dispCenter[1][1] = 64;
        dispCenter[2][0] = 20;
        dispCenter[2][1] = 60;
        dispCenter[3][0] = 44;
        dispCenter[3][1] = 60;

        switch(dir){
            case 0:
                up = true;
                super.setCenter(dispCenter[0][0], dispCenter[0][1]);
                break;
            case 1:
                down = true;
                super.setCenter(dispCenter[1][0], dispCenter[1][1]);
                break;
            case 2:
                left = true;
                super.setCenter(dispCenter[2][0], dispCenter[2][1]);
                break;
            case 3:
                right = true;
                super.setCenter(dispCenter[3][0], dispCenter[3][1]);
                break;
            default:
                up = true;
        }
//        bulletHit.volume(-7.5f);
        AudioManager.getInstance().volume(AudioManager.BULLET_HIT, -7.5f);
    }

    @Override
    public void update(){
        if (down){
            animation.setFrames(downSprites);
        }
        else if (left){
            animation.setFrames(leftSprites);
        }
        else if (right){
            animation.setFrames(rightSprites);
        }
        else if (up){
            animation.setFrames(upSprites);
        }
    }

    @Override
    public void draw(Graphics2D g){
        if (hit)
            animation.update(0,2);
        else
            animation.update(3,5);
        if(animation.getCurrentFrame()==4){
            moving = false;
        }
        g.drawImage(animation.getFrame(), posX, posY, null);
    }
    
    @Override
    public void walk(){
        rowTile = cRY / tileSize;
        colTile = cRX / tileSize;
        if(!hit) return;
        if(up){
            setCenter(dispCenter[0][0],dispCenter[0][1]);
            if (posRY < 1){
                hit = false;
            }
            else if(tm.isBlocked(aux[0][1], colTile)){
//                bulletHit.stop();
//                bulletHit.play();
                AudioManager.getInstance().stop(AudioManager.BULLET_HIT);
                AudioManager.getInstance().play(AudioManager.BULLET_HIT);
                hit = false;
            }
            else{
                posRY -= speed;
                posY -= speed;
            }
        }
        if(down){
            setCenter(dispCenter[1][0], dispCenter[1][1]);
            if(rowTile >= tm.getNumRows()-1){
                posY += speed;
                posRY += speed;
                if(posRY >= tm.getMapHeight() - height){
                    hit = false;
                }
            }
            else if(tm.isBlocked(aux[1][1], colTile)){
//                bulletHit.stop();
//                bulletHit.play();
                //return;
                AudioManager.getInstance().stop(AudioManager.BULLET_HIT);
                AudioManager.getInstance().play(AudioManager.BULLET_HIT);
                hit = false;
            }
            else{
                posY += speed;
                posRY += speed;
            }
        }
        if(left){
            setCenter(dispCenter[2][0], dispCenter[2][1]);
            if (colTile <= 0){
                posX -= speed;
                posRX -= speed;
                if (posRX <= 0){
                    hit = false;
                }
            }
            else if(tm.isBlocked(rowTile, aux[2][1])){
//                bulletHit.stop();
//                bulletHit.play();
                AudioManager.getInstance().stop(AudioManager.BULLET_HIT);
                AudioManager.getInstance().play(AudioManager.BULLET_HIT);
                hit = false;
            }
            else{
                posX -= speed;
                posRX -= speed;
            }
        }
        if(right){
            setCenter(dispCenter[3][0],dispCenter[3][1]);
            if(colTile == tm.getNumCols()-1){
                posX += speed;
                posRX += speed;
                if(posRX >= tm.getMapWidth() - width){
                    posRX = tm.getMapWidth() - width;
                    hit = false;
                }
            }
            else if(tm.isBlocked(rowTile, aux[3][1])){
//                bulletHit.stop();
//                bulletHit.play();
                AudioManager.getInstance().stop(AudioManager.BULLET_HIT);
                AudioManager.getInstance().play(AudioManager.BULLET_HIT);
                hit = false;
            }
            else{
                posX += speed;
                posRX += speed;
            }
        }
    }

    public void crash(ShooterEntity obj){
        if (intersects(obj)){
//        if(obj.getRowTile() == rowTile && obj.getColTile() == colTile){
            AudioManager.getInstance().stop(AudioManager.BULLET_HIT);
            AudioManager.getInstance().play(AudioManager.BULLET_HIT);
            obj.damage(damage);
            hit = false;
        }
    }
    
    public void crash(List<Enemy> targets){
        targets.forEach((obj) ->{
            if (intersects(obj)){
//                bulletHit.stop();
//                bulletHit.play();
//            if(obj.getRowTile() == rowTile && obj.getColTile() == colTile){
                AudioManager.getInstance().stop(AudioManager.BULLET_HIT);
                AudioManager.getInstance().play(AudioManager.BULLET_HIT);
                obj.setHitted(true);
                obj.damage(damage);
                hit = false;
            }
        });
        
    }
    
    private void bulletPreferences(int type, int damage){
        switch (type) {
            case 0:
                sprite = TextureManager.spriteEnemyBullet;
                this.damage = 1;
                break;
            case 1:
                sprite = TextureManager.spritePlayerBullet;
                this.damage = damage;
                break;
            case 2:
                sprite = TextureManager.spriteBossBullet;
                this.damage = 5;
                break;
            default:
                throw new AssertionError();
        }
    }
}
