package app.main;

import app.manager.KeyManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * @author Kevin
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
    /************ DECLARACION DE VARIABLES ************/
    //Dimensiones
    public static final int PANEL_WIDTH = 640;
    public static final int PANEL_HEIGHT = 640;
    
    //Game thread
    private Thread thread;
    private volatile boolean running;
    private volatile boolean gameOver;
    private final int FPS = 60;
    private final long TARGET_TIME = 1000/FPS;
    
    //Graphics
    private BufferedImage image;
    private Graphics2D g;

    /************ FINAL DECLARACION DE VARIABLES ************/
    
    public GamePanel(){
        super();
        super.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        super.setFocusable(true);
        super.requestFocus();
    }
    
    
    //METODOS OVERRIDE
    @Override
    public void addNotify(){
        super.addNotify();
        if (thread == null){
            super.addKeyListener(this);
            thread = new Thread(this);
            thread.start();
        }
    }
    
    @Override
    public void run(){
        init();
        long start;
        long elapsed;
        long wait;
        
        while (running){
            start = System.nanoTime();
            update();
            render();
            draw();
            elapsed = System.nanoTime() - start;
            wait = TARGET_TIME - elapsed/1000000;
            if (wait < 0) wait = TARGET_TIME;
            
            try {
                Thread.sleep(wait);
            }
            catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent key){
    }
    
    @Override
    public void keyPressed(KeyEvent key){
        KeyManager.keySet(key.getKeyCode(), true);
    }
    
    @Override
    public void keyReleased(KeyEvent key){
        KeyManager.keySet(key.getKeyCode(), false);
    }
    
    // MIS METODOS
    private void init(){
        running = true;
        image = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
    }
    
    private void update(){
        KeyManager.update();
    }
    
    private void render(){
    }
    
    private void draw(){
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
        g2.dispose();
    }
}
