package Entity;

import Manager.TextureManager;
import java.awt.Graphics2D;
import TileMap.TileMap;
import java.util.Random;

/**
 *
 * @author Gama
 */
public class Enemy extends ShooterEntity{
    private final int[] f = new int[2];
    
    public Enemy(TileMap tm){
        super(tm);
        life = 150;
        width = 64;
        height = 64;
        cwidth=cheight=58;
        downSprites = TextureManager.spriteEnemy[0];
        leftSprites = TextureManager.spriteEnemy[2];
        rightSprites = TextureManager.spriteEnemy[3];
        upSprites = TextureManager.spriteEnemy[3];
        
        bulletC = new BulletController(6);
        
        speed = 2;
        delay = 2;
        animation.setFrames(downSprites);
        animation.setDelay(delay);
        
        f[0] = 12; f[1] = 13;
        
        dispCenter[0][0] = 32;
        dispCenter[0][1] = 54;
        dispCenter[1][0] = 32;
        dispCenter[1][1] = 64;
        dispCenter[2][0] = 20;
        dispCenter[2][1] = 60;
        dispCenter[3][0] = 44;
        dispCenter[3][1] = 60;
    }
    
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    public boolean isMoving(){
        return moving;
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
            animation.setFrames(leftSprites);
        }
        else if (up){
            animation.setFrames(downSprites);
        }
        if(hitted){
            animation.setFrames(upSprites);
        }
        hitted = false;
        walk();
    }
    
    @Override
    public void walk(){
        try {
            rowTile = cRY / tileSize;
            colTile = cRX / tileSize;
            if(up){
                lastDir = 0;
                setCenter(dispCenter[0][0],dispCenter[0][1]);
                if (posRY < 1){
                    posY = 0;
                    posRY = 0;
                }
                else if(tm.isBlocked(aux[1][1], colTile)){
                    return;
                }
                else{
                    posY -= speed;
                    posRY -= speed;
                }
            }
            if(down){
                lastDir = 1;
                setCenter(dispCenter[1][0], dispCenter[1][1]);
                if(rowTile >= tm.getNumRows()-1){
                    if(posRY >= tm.getMapHeight() - height){
                        posRY = tm.getMapHeight() - height-1;
                    }
                }
                else if(tm.isBlocked(aux[1][1], colTile)){
                    return;
                }
                else{
                    posY += speed;
                    posRY += speed;
                }
            }
            if(left){
                lastDir = 2;
                setCenter(dispCenter[2][0], dispCenter[2][1]);
                if (colTile == 0 && !tm.isBlocked(rowTile, aux[2][1])){
                    if (posRX <= -10){
                        posRX = -10;
                    }
                }
                else if(tm.isBlocked(rowTile, aux[2][1])){
                    return;
                }
                else{
                    posX -= speed;
                    posRX -= speed;
                }
            }
            if(right){
                lastDir = 3;
                setCenter(dispCenter[3][0], dispCenter[3][1]);
                if(colTile == tm.getNumCols()-1 && !tm.isBlocked(rowTile, aux[3][1])){
                    if(posRX >= tm.getMapWidth() - width + 10){
                        posRX = tm.getMapWidth() - width + 10;
                    }
                }
                else if(tm.isBlocked(rowTile, aux[3][1])){
                    return;
                }
                else{
                    posX += speed;
                    posRX += speed;
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void draw(Graphics2D g){
        if(defeated){
            animation.update(14, 21);
            if(animation.getCurrentFrame()==21){
                moving = false;
                shooting = false;
            }
        }
        else if(moving && shooting){
            animation.update(0, 11); //disparar y caminar
        }
        else if (moving){
            animation.update(0,11); // caminar
        }
        else if(shooting){
            animation.update(0,12); // detenido y disparando
        }
        g.drawImage(animation.getFrame(), posX, posY, null);
        visualize(g);
    }
    
    @Override
    public void setDown() {
        super.setDown();
        up = left = right = false;
    }
    
    @Override
    public void setLeft() {
        super.setLeft();
        up = down = right = false;
    }
    
    @Override
    public void setRight() {
        super.setRight();
        up = down = left = false;
    }
    
    @Override
    public void setUp() {
        super.setUp();
        down = left = right = false;
    }
    
    public void setSpeed(boolean flag){
        super.setSpeed(flag?5:2);
    }
    
    public void setDelay(boolean flag){
        animation.setDelay(flag? 3 : 4);
    }

     public void defeated(){
        defeated = true;
    }
    
    public void setPosInicial(int camX, int camY){
        try {
            int row, col;
            row = new Random().nextInt(tm.getNumRows());
            col = new Random().nextInt(tm.getNumCols());
            if (tm.isBlocked(row, col)){
                //System.out.printf("BLOCKED: %d, %d\n", row+1, col+1);
                setPosInicial(camX, camY);
            }
            else {
                //System.out.printf("FREE: %d, %d\n", row+1, col+1);
                posRX = col*width;
                posRY = row*height;
                posX = posRX - camX;
                posY = posRY - camY;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
