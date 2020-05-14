package GameState;

import Main.GamePanel;
import Manager.GameStateManager;
import Manager.KeyManager;
import Manager.LanguageManager;
import Manager.TextManager;
import Manager.TextureManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ScoreState extends GameState {
    
    private final int SCORE_STATE = 5;
    
    List<String[]> scores;
    
    private final String FILENAME = ".score";
    
    private int currentOption;
    
    private final String titles[] = {
        LanguageManager.getInstance().get("nombre"),
        LanguageManager.getInstance().get("puntaje")
    };
    
    private final String options[] = {
        LanguageManager.getInstance().get("regresar"),
        LanguageManager.getInstance().get("salir")
    };
    
    public ScoreState(GameStateManager gsm){
        super(gsm);
        scores = new ArrayList<>();
        loadScores();
    }
    
    @Override
    public void init() {
        currentOption = 0;
    }

    @Override
    public void update() {
        handleEvents();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(TextureManager.menu, 0, 0,GamePanel.PANEL_WIDTH,GamePanel.PANEL_HEIGHT, null);
        g.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 35));
        
        g.setColor(Color.RED);
        g.drawString(titles[0], 180, 215);
        g.drawString(titles[1], 350, 215);
        
        g.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 30));
        g.setColor(Color.BLACK);
        for (int i = 0; i < scores.size(); i++){
            g.drawString(scores.get(i)[0], 180, 255+35*i);
            g.drawString(scores.get(i)[1], 350, 255+35*i);
        }
        g.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 35));
        for (int i = 0; i < options.length; i++) {
            if (currentOption == i){
                g.setColor(Color.RED);
                g.drawImage(TextureManager.arrow, 165 + currentOption*180, 485, 30, 30, null); //(imagen, x, y,ancho imagen,largo imagen,null)
            }
            else{
                g.setColor(Color.BLACK);
            }
            g.drawString(options[i], 195 + 180*i, 510);
        }
    }

    @Override
    public void handleEvents() {
        if (KeyManager.isPressed(KeyManager.ESCAPE)){
            gsm.popState();
        }
        else if (KeyManager.isPressed(KeyManager.RIGHT) && currentOption < options.length -1 ){
            currentOption++;
        }
        else if (KeyManager.isPressed(KeyManager.LEFT) && currentOption > 0){
            currentOption--;
        }
        else if (KeyManager.isPressed(KeyManager.ENTER)){
            selectOption();
        }
    }

    @Override
    public void exit() {
        
    }

    @Override
    public int getStateID() {
        return SCORE_STATE;
    }
    
    private void selectOption(){
        if (currentOption == 0){
            gsm.popState();
        }
        else if (currentOption == 1){
            System.exit(0);
        }
    }
    
    private void loadScores(){
        File file = new File(FILENAME);
        try{
            if (file.exists()){
                Scanner sc = new Scanner(file);
                String line;
                while (sc.hasNext()){
                    line = sc.nextLine();
                    scores.add(line.split("="));
                }
            }
            else{
                PrintWriter pw = new PrintWriter(file);
                pw.close();
                loadScores();
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

}
