package Entity;

import TileMap.TileMap;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public abstract class ShooterEntity extends Entity{
    
    protected BufferedImage downSprites[];
    protected BufferedImage leftSprites[];
    protected BufferedImage rightSprites[];
    protected BufferedImage upSprites[];
    
    protected final int DOWN = 0;
    protected final int LEFT = 1;
    protected final int RIGHT = 2;
    protected final int UP = 3;
    
    //Movimiento
    protected boolean moving;
    protected boolean shooting;
    protected boolean left;
    protected boolean up;
    protected boolean right;
    protected boolean down;
    
    //Atributos
    protected int speed;
    
    //Ultima direccion a la que está viendo
    protected int lastDir;
    
    protected BulletController bulletC;
    
    protected int life;
    protected int maxLife;
    
    protected boolean hitted;
    protected boolean defeated;
    
    public ShooterEntity(TileMap tm) {
        super(tm);
        moving = false;
        shooting = false;
    }
    
    /**
     * 
     * @return 
     */
    public int getDirection(){
        return lastDir;
    }
    
    /**
     * 
     * @return speed
     * Velocidad del objeto
     */
    public int getSpeed(){
        return this.speed;
    }
   
    /**
     * 
     * @return life
     * Vida restante del personaje
     */
    public int getLife(){
        return life;
    }
    
    /**
     * 
     * @return maxLife -
     * Vida maxima del personaje
     */
    public int getMaxLife(){
        return maxLife;
    }
    
     /**
     * Devuelve un objeto de tipo BulletController
     * @return bulletC -
     * Manejador de balas por entidad
     */
    public BulletController getBulletController(){
        return bulletC;
    }
    
    //Metodos Set

    /**
    * Especifica si la entidad está o no disparando/atacando
    * @param shooting
    * Debe ser true si la entidad se está moviendo, false si no.
    */
    public void setShooting(boolean shooting){
        this.shooting = shooting;
    }
    
    /**
    * Especifica si la entidad está o no moviéndose
    * @param moving
    * Debe ser true si la entidad se está moviendo, false si no.
    */
    public void setMoving(boolean moving){
        this.moving = moving;
    }
    
    public void setHitted(boolean hitted){
        this.hitted = hitted;
    }
    
    public void damage(int d){
        life -= d;
    }
    
    /**
     * Hace que la entidad vea hacia abajo
     */
    public void setDown(){
        down = true;
    }
    
    /**
    * Hace que la entidad vea hacia la izquierda
    */
    public void setLeft(){
        left = true;
    }
    
    /**
    * Hace que la entidad vea hacia la derecha
    */
    public void setRight(){
        right = true;
    }
    
    /**
     * Hace que la entidad vea hacia arriba
     */
    public void setUp(){
        up = true;
    }
    
    /**
     *  Especifica la distancia de paso que la entidad se moverá
     * @param speed
     * Valor en pixeles del paso
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    public boolean isDefeated(){
        return defeated;
    }

    
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
        g.drawOval(posX+32-2, posY+54-2, 2, 2);
        g.drawOval(posX+32-2, posY+64-2, 2, 2);
        g.drawOval(posX+20-2, posY+60-2, 2, 2);
        g.drawOval(posX+44-2, posY+60-2, 2, 2);   
    }
    
    /**
     * Permite el movimiento del personaje acorde al TileMap dado
     */
    protected abstract void walk();
    

    @Override
    public abstract void draw(Graphics2D g);

    @Override
    public abstract void update();
}
