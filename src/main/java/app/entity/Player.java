package app.entity;

import app.main.GamePanel;
import app.manager.TextureManager;
import java.awt.Graphics2D;
import app.tilemap.TileMap;
/**
 * @author Kevin
 */
public class Player extends ShooterEntity {
    
    private final int normalSpeed = 2;
    private final int maxSpeed = 3;
    
    
    private final int[] f = new int[2];
    
    public Player(TileMap tm){
        super(tm);
        init();
    }
    
    private void init(){
        maxLife = life = 150;
        width = 64;
        height = 64;
        cwidth=56;
        cheight=56;
        downSprites = TextureManager.spritePlayer[DOWN];
        leftSprites = TextureManager.spritePlayer[LEFT];
        rightSprites = TextureManager.spritePlayer[RIGHT];
        upSprites = TextureManager.spritePlayer[UP];
        bulletC = new BulletController(1);
        
        speed = normalSpeed;
        
        delay = 4;
        animation.setFrames(downSprites);
        animation.setDelay(delay);
        posX = tm.getPosInicialX();
        posY = tm.getPosInicialY();
        posRX = tm.getPosInicialRX();
        posRY = tm.getPosInicialRY();
        
        f[0] = 9; f[1] = 17;
        
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
    
    public void setLife(int life) {
        this.life = life;
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
        walk();
        down = left = right = up = false;
    }
    
    @Override
    public void draw(Graphics2D g){        
        /* frames 0-7 : camina
        * frames 8-15: camina mientras dispara
        * frames 8 y 16: dispara sin caminar
        */
        if(moving && shooting){
            animation.update(8, 15); //disparar y caminar
        }
        else if (moving){
            animation.update(0,7); // caminar
        }
        else if(shooting)
            animation.update(f); // detenido y disparando
        
        else{
            animation.setFrame(0);//detenido
        }
        
        g.drawImage(animation.getFrame(), posX, posY, null);
        
        //Ver posiciones 
        visualize(g);
    }
    
    @Override
    public void setDown() {
        super.setDown();
    }
    
    @Override
    public void setLeft() {
        super.setLeft();
    }
    
    @Override
    public void setRight() {
        super.setRight();
    }
    
    @Override
    public void setUp() {
        super.setUp();
    }
    
    public void setSpeed(boolean flag){
        super.setSpeed(flag?maxSpeed:normalSpeed);
    }
    
    public void setDelay(boolean flag){
        animation.setDelay(flag? 3 : 4);
    }
    
    public void setMap(TileMap tm){
        this.tm = tm;
        posX = tm.getPosInicialX();
        posY = tm.getPosInicialY();
        posRX = tm.getPosInicialRX();
        posRY = tm.getPosInicialRY();
    }

    @Override
    protected void walk() {
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
                else if(tm.isBlocked(aux[0][1], colTile)){
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
                    posY += speed;
                    posRY += speed;
                    if(posRY >= tm.getMapHeight() - height){
                            posY = GamePanel.PANEL_HEIGHT - height-1;
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
                    posX -= speed;
                    posRX -= speed;
                    if (posRX <= -10){
                            posX = -10;
                            posRX = -10;
                    }
                }
                else if(tm.isBlocked(rowTile, aux[2][1])){return;
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
                    posX += speed;
                    posRX += speed;
                    if(posRX >= tm.getMapWidth() - width+10){
                            posX = GamePanel.PANEL_WIDTH - width+10;
                            posRX = tm.getMapWidth() - width+10;
                    }
                }
                else if(!tm.isBlocked(rowTile, aux[3][1])){
                    posX += speed;
                    posRX += speed;
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
}
