package app.entity;

import java.awt.image.BufferedImage;

/**
 * @author Kevin
 */
public class Animation {
    private BufferedImage frames[];
    private int currentFrame;
    private int numFrames;
    
    private int count;
    private int delay;
    
    private int timesPlayed;
    
    public Animation(){
        timesPlayed = 0;
        delay = 4;
    }
    
    public void setFrames(BufferedImage frames[]){
        this.frames = frames;
        //currentFrame = 0;
        //count = 0;
        timesPlayed = 0;
        
        numFrames = frames.length;
    }
    
    public void setDelay(int delay){
        this.delay = delay;
    }
    
    public void setFrame(int currentFrame){
        this.currentFrame = currentFrame;
    }
    
    public int getCurrentFrame(){
        return this.currentFrame;
    }
        
/**
     *Función que itera una animacion en un rango de frames dados
     *@param i
     * Numero de frame inicial
     *@param f
     * Numero de frame final
     */
    public void update(int i, int f){
        if (delay == -1){
            return;
        }
        count++;
        if (count >= delay){
            currentFrame++;
            count = 0;
        }
        if (currentFrame > f){
            currentFrame = i;
            timesPlayed = 0;
        }
        //System.out.println(delay);
        //System.out.println("Current:" + currentFrame+", count:"+count);
    }
    
    int c = 0;
    /**Funcion para iterar una animación con frames dados no consecutivos
     *@param frames
     * Arreglo de enteros que ontiene los # de frames para iterar
     */
    public void update(int[] frames){
        if (delay == -1){
            return;
        }
        currentFrame = frames[c]-1;
        count++;
        if (count >= delay){
            c++;
            count = 0;
        }
        if (c == frames.length){
            currentFrame = frames[0]-1;
            c = 0;
            timesPlayed = 0;
        }
        //System.out.println(delay);
        //System.out.println("Current:" + currentFrame+"\n");
    }
    
    public void update(){
        if (delay == -1){
            return;
        }
        count++;
        if (count == delay){
            currentFrame++;
            count = 0;
        }
        if (currentFrame == numFrames){
            currentFrame = 0;
            timesPlayed = 0;
        }
    }
    
    public BufferedImage getFrame(){
        return frames[currentFrame];
    }
}
