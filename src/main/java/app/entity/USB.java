package app.entity;

import app.manager.TextureManager;
import app.tilemap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Gama
 */
public class USB extends Entity{
    public USB(TileMap tm){
        super(tm);
        init();
    }
    protected BufferedImage[] sprite;
    private boolean taken;
    
    public boolean isTaken(){
        return taken;
    }
    
    public void setTaken(){
        taken = true;
    }
    
    private void init(){
        width = 64;
        height = 64;
        cwidth = cheight = 58;
        sprite = TextureManager.USB;
        animation.setFrames(sprite);
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
        
    }
    
    @Override
    public void draw(Graphics2D g){
        animation.update();
        g.drawImage(animation.getFrame(), posX, posY, null);
    }
}
