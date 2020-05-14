package Entity;

import Manager.TextureManager;
import TileMap.TileMap;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;


public class BossEnemy extends Enemy {

    public BossEnemy(TileMap tm) {
        super(tm);
        life = 500;
        width = 128;
        height = 128;
        cwidth=cheight=100;
        
        downSprites = TextureManager.spriteBoss[0];
        leftSprites = TextureManager.spriteBoss[2];
        rightSprites = TextureManager.spriteBoss[2];
        upSprites = TextureManager.spriteBoss[3];
        
        bulletC = new BulletController(4);
        
        speed = 2;
        delay = 2;
        animation.setFrames(downSprites);
        animation.setDelay(delay);
        
        dispCenter[0][0] = 64;
        dispCenter[0][1] = 30;
        dispCenter[1][0] = 64;
        dispCenter[1][1] = 110;
        dispCenter[2][0] = 20;
        dispCenter[2][1] = 96;
        dispCenter[3][0] = 108;
        dispCenter[3][1] = 96;
    }
    
    @Override
    public void setPosInicial(int camX, int camY){
        try {
            int row, col;
            row = new Random().nextInt(tm.getNumRows()-4);
            col = new Random().nextInt(tm.getNumCols()-4);
            if (tm.isBlocked(row, col)){
                setPosInicial(camX, camY);
            }
            else {
                posRX = col*64;
                posRY = row*64;
                posX = posRX - camX;
                posY = posRY - camY;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void visualize(Graphics2D g){
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.setColor(Color.white);
        //g.drawString(("Moving: "+moving), posX, posY-20);
        //g.drawString(("Shooting: "+shooting), posX, posY-30);
        //g.drawString(("Tile: "+rowTile+", "+colTile), posX, posY-10);
        //g.drawString(("PosR: "+posRX+", "+posRY), posX, posY);
        g.drawString("LIFE:"+life, posX, posY-5);
        //Visualizar los centros
        g.setColor(Color.WHITE);
//        g.drawOval(posX+32-2, posY+54-2, 2, 2);
//        g.drawOval(posX+32-2, posY+64-2, 2, 2);
//        g.drawOval(posX+20-2, posY+60-2, 2, 2);
//        g.drawOval(posX+44-2, posY+60-2, 2, 2);   
        g.drawOval(posX+dispCenter[0][0]-2, posY+dispCenter[0][1]-2, 2, 2);
        g.drawOval(posX+dispCenter[1][0], posY+dispCenter[1][1]-2, 2, 2);
        g.drawOval(posX+dispCenter[2][0]-2, posY+dispCenter[2][1]-2, 2, 2);
        g.drawOval(posX+dispCenter[3][0]-2, posY+dispCenter[3][1]-2, 2, 2);   
    }
    
    int count;
    @Override
    public void draw(Graphics2D g){
        if(defeated){
            animation.setFrames(TextureManager.xplodeBoss);
            animation.update(0, 11);
            if(animation.getCurrentFrame()==5){
                if(count <= 10){
                    count++;
                    animation.setFrame(0);
                }
            }
            if(animation.getCurrentFrame() == 11){
                moving = shooting = false;
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
}
