package app.entity;

import app.manager.TextureManager;
import app.tilemap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Gama
 */

public class Coin extends Entity{
    public Coin(TileMap tm){
        super(tm);
        init();
    }
    
    protected boolean taken;
    
    public void taken(){
        taken = true;
    }
    
    public boolean isTaken(){
        return taken;
    }
    
    protected BufferedImage[] sprite;
    int count = 0;
    
    private void init(){
        width =32;
        height = 32;
        cwidth = 32;
        cheight = 32;
        sprite = TextureManager.coin;
        animation.setFrames(sprite);
        taken = false;
    }
    
    public void setRealPosition(int posRX, int posRY){
        this.posRX = posRX;
        this.posRY = posRY;
    }
    
    public void setPosition(int camX, int camY){
        rowTile = (posRY / 64);
        colTile = (posRX / 64);
        posX = posRX - camX;
        posY = posRY - camY;
    }
    
    @Override
    public void update(){
        if(count < 400)
            count++;
    }
    
    public int getCount(){
        return count;
    }
    
    @Override
    public void draw(Graphics2D g){
        //System.out.println((count > 499));
        if(!(!taken && (count > 399))){
            animation.update();
            g.drawImage(animation.getFrame(), posX, posY, null);
        }
    }
}
