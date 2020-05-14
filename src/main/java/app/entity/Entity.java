package app.entity;

import java.awt.Graphics2D;
import app.tilemap.TileMap;
import java.awt.Rectangle;

public abstract class Entity {
    //Dimensiones
    protected int width;
    protected int height;
    protected int cwidth;
    protected int cheight;
    
    //Posicion
    
    //Posiciones de dibujo
    protected int posX, posY;
    //Posicion real de dibujo en el escenario
    protected int posRX, posRY;
    //Centro real del personaje en el escenario
    protected int cRX, cRY;
    //Auxiliar para saber si los alrededores están bloqueados
    protected int[][] aux = new int[4][2];
    
    /**Desplazamiento del centro en las 4 direcciones.
    *Cada par representa el X y el Y que se desea desplazar el centro de
    *la entidad
    */
    protected int[][] dispCenter = new int[4][2];
    protected int rowTile;
    protected int colTile;    
    
    
    //Animacion
    protected Animation animation;
    protected int delay;
    
    protected TileMap tm;
    protected int tileSize;
    
    //Constructor

    /**
     * Clase abstacta para entidades del juego.
     * @param tm
     * Mapa del juego donde la entidad podrá moverse.
     * Este es un arreglo quemado escrito en archivos que especifica los 
     * obtaculos de la pantalla de juego
     */
    public Entity(TileMap tm){
        this.tm = tm;
        tileSize = tm.getTileSize();
        animation = new Animation();
        posX =0;
        posY=0;
    }
    
    //Metodos Get

    /**
     *
     * @return
     * Devuelve posición de dibujo en pantalla en X de la entidad
     */
    public int getPosX(){
        return posX;
    }
    
    /**
     *
     * @return
     * Devuelve posicion de dibujo en pantalla Y de la entidad
     */
    public int getPosY(){
        return posY;
    }

    /**
     *
     * @return
     * Devuelve posicion real en X de la entidad, es decir
     * su posición real en el escenario
     */
    public int getPosRX() {
        return posRX;
    }

    /**
     *
     * @return
     * Devuelve posicion real en X de la entidad, es decir
     * su posición real en el escenario
     */
    public int getPosRY() {
        return posRY;
    }
    
    /**
     *
     * @return
     * Devuelve la fila en la que esta ubicada la entidad en la matriz app.TileMap
     */
    public int getRowTile(){
        return rowTile;
    }
    
    /**
     *
     * @return
     * Devuelve la columna en la que esta ubicada la entidad en la matriz app.TileMap
     */
    public int getColTile(){
        return colTile;
    }
    
    
    /**
     *  Especifica que tan rápido itera la animacion del personaje.
     *  Mientras menor sea, más rapido se hará la animación
     * @param delay
     */
    public void setDelay(int delay){
        this.delay = delay;
    }
    
    public boolean intersects(Entity o){
        return getBounds().intersects(o.getBounds());
    }
    
    public Rectangle getBounds(){
        return new Rectangle(posX, posY, cwidth, cheight);
    }
    
    
    
    /**
     * Dibuja la entidad respecto a las variables actualizadas por update()
     * @param g
     */
    public abstract void draw(Graphics2D g);
    
    /**
     * Especifica un centro relativo de la entidad.
     * Este centro permite que, visualmente, el personaje no se detenga
     * antes o después de los obtáculos
     * @param x
     * @param y
     */
    protected void setCenter(int x, int y){
        cRX = posRX + x;
        cRY = posRY + y;
        setSurroundigs();
    }
    
    /**
     * Especifica alrededores respecto al centro dado en setCenter().
     * Esto es util para el calculo de la proximidad de los obstaculos en el 
     * mapa, ya que se puede saber con anticipación si el personaje está por
     * llegar a uno.
     * Si esto se calculara directamente con cRX y cRY, el personaje sería
     * incapaz de moverse al llegar a un barrera.
     */
    private void setSurroundigs(){
        //posición alrededores
        //arriba
        aux[0][0] = cRY - 2;
        //abajo
        aux[1][0] = cRY + 2;
        //izquierda
        aux[2][0] = cRX - 2;
        //derecha
        aux[3][0] = cRX + 2;
        //tile con col constante
        aux[0][1] = aux[0][0] / tileSize;
        aux[1][1] = aux[1][0] / tileSize;
        //tile con row constante
        aux[2][1] = aux[2][0] / tileSize;
        aux[3][1] = aux[3][0] / tileSize;
    }
    
         //0-arriba 1-abajo 2-izq 3-der
    public void redraw(int dir, int sp){
        switch(dir){
            case 0:
                posY += sp;
                break;
            case 1:
                posY -= sp;
                break;
            case 2:
                posX += sp;
                break;
            case 3:
                posX -= sp;
                break;
            default:
                break;
        }
    }
    
    /**
     * Actualiza las variables necesarias para el movimiento de la entidad  
     */
    public abstract void update();
}
